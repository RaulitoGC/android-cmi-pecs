package com.cmi.presentation.pecs

import com.cmi.presentation.model.PictogramModel

class PecsFlowContentHolder(
    private val pictogramModels: MutableList<PictogramModel>
){

    fun save(pictogramModel: PictogramModel){
        when{
            pictogramModel.isAction() -> {
                if(pictogramModel.isPrimaryAction()){
                    pictogramModels[PRIMARY_ACTION_IDX] = pictogramModel
                } else {
                    pictogramModels[SECONDARY_ACTION_IDX] = pictogramModel
                }
            }

            pictogramModel.isAttribute() -> {
                if(pictogramModel.isPrimaryAttribute()) {
                    pictogramModels[PRIMARY_ATTRIBUTE_IDX] = pictogramModel
                } else {
                    pictogramModels[SECONDARY_ATTRIBUTE_IDX] = pictogramModel
                }
            }

            else  -> {
                pictogramModels[MAIN_PICTOGRAM_IDX] = pictogramModel
            }
        }
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

    private fun PictogramModel.isAction(): Boolean {
        return id == ACTION_CATEGORY_ID
    }

    private fun PictogramModel.isAttribute(): Boolean {
        return id == ATTRIBUTE_CATEGORY_ID
    }

    private fun PictogramModel.isPrimaryAttribute(): Boolean {
        val currentPrimaryAttributePictogram = pictogramModels[PRIMARY_ATTRIBUTE_IDX]
        return currentPrimaryAttributePictogram.id != null
    }

    fun getPictureModels() = pictogramModels

    companion object {
        /**
         * This is the ID of Want action pictogram Model
         * in the context of children, it must be taken
         * as a first action
         */
        private const val PICTOGRAM_WANT_ID = 186

        private const val ATTRIBUTE_CATEGORY_ID = 2
        private const val ACTION_CATEGORY_ID = 18

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