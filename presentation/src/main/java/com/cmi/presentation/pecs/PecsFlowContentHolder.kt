package com.cmi.presentation.pecs

import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.model.isAction
import com.cmi.presentation.model.isAttribute

class PecsFlowContentHolder(
    private val pictogramModels: MutableList<PictogramModel>
){

    fun save(pictogramModel: PictogramModel, onComplete: (pictograms : List<PictogramModel>) -> Unit){
        onUpdate(pictogramModel, PecsFlowContentUpdater.Add, onComplete)
    }

    fun remove(pictogramModel: PictogramModel, onComplete: (pictograms : List<PictogramModel>) -> Unit) {
        onUpdate(pictogramModel, PecsFlowContentUpdater.Remove, onComplete)
    }

    private fun onUpdate(pictogramModel: PictogramModel, type: PecsFlowContentUpdater, onComplete: (pictograms : List<PictogramModel>) -> Unit) {
        val pictogramToUpdate = when(type){
            is PecsFlowContentUpdater.Add -> {
                pictogramModel
            }

            is PecsFlowContentUpdater.Remove -> {
                PictogramModel()
            }
        }

        when{
            pictogramModel.isAction() -> {
                if(pictogramModel.isPrimaryAction()){
                    pictogramModels[PRIMARY_ACTION_IDX] = pictogramToUpdate
                } else {
                    pictogramModels[SECONDARY_ACTION_IDX] = pictogramToUpdate
                }
            }

            pictogramModel.isAttribute() -> {
                if(type == PecsFlowContentUpdater.Add ) {
                    if(isPrimaryAttributeAvailable()) {
                        pictogramModels[PRIMARY_ATTRIBUTE_IDX] = pictogramToUpdate
                    } else if(isSecondaryAttributeDifferentFromPrimary(pictogramToUpdate)){
                        pictogramModels[SECONDARY_ATTRIBUTE_IDX] = pictogramToUpdate
                    }
                } else {
                    if(isPrimaryAttributeSelected(pictogramModel)) {
                        pictogramModels[PRIMARY_ATTRIBUTE_IDX] = pictogramToUpdate
                    } else if(isSecondaryAttributeSelected(pictogramModel)) {
                        pictogramModels[SECONDARY_ATTRIBUTE_IDX] = pictogramToUpdate
                    }
                }

            }

            else  -> {
                pictogramModels[MAIN_PICTOGRAM_IDX] = pictogramToUpdate
            }
        }

        onComplete(getPictureModels())
    }

    fun init(){
        pictogramModels.clear()
        repeat(MAX_PICTOGRAMS){
            pictogramModels.add(PictogramModel())
        }
    }

    private fun PictogramModel.isPrimaryAction(): Boolean {
        return id == PICTOGRAM_WANT_ID
    }

    private fun isPrimaryAttributeAvailable(): Boolean {
        val primaryAttribute = pictogramModels[PRIMARY_ATTRIBUTE_IDX]
        return  primaryAttribute.id == null
    }

    private fun isSecondaryAttributeDifferentFromPrimary(pictogramModel: PictogramModel): Boolean {
        val primaryAttribute = pictogramModels[PRIMARY_ATTRIBUTE_IDX]
        return primaryAttribute.id != pictogramModel.id
    }

    private fun isPrimaryAttributeSelected(pictogramModel: PictogramModel): Boolean {
        val primaryAttribute = pictogramModels[PRIMARY_ATTRIBUTE_IDX]
        return primaryAttribute.id == pictogramModel.id
    }

    private fun isSecondaryAttributeSelected(pictogramModel: PictogramModel): Boolean {
        val secondaryAttribute = pictogramModels[SECONDARY_ATTRIBUTE_IDX]
        return secondaryAttribute.id == pictogramModel.id
    }

    fun getPictureModels() = pictogramModels.filter { it.id != null }

    companion object {
        /**
         * This is the ID of Want action pictogram Model
         * in the context of children, it must be taken
         * as a first action
         */
        private const val PICTOGRAM_WANT_ID = 186

        /**
         * Max number of pictograms that are allowed in Stripe
         */
        private const val MAX_PICTOGRAMS = 5

        /**
         * Index identifiers for list of pictograms allowed in Stripe
         */
        private const val PRIMARY_ACTION_IDX = 0
        private const val SECONDARY_ACTION_IDX = 1
        private const val MAIN_PICTOGRAM_IDX = 2
        private const val PRIMARY_ATTRIBUTE_IDX = 3
        private const val SECONDARY_ATTRIBUTE_IDX = 4
    }
}

sealed class PecsFlowContentUpdater{
    data object Remove: PecsFlowContentUpdater()
    data object Add: PecsFlowContentUpdater()
}