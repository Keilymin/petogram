package com.burlakov.petogram.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import com.burlakov.petogram.PetogramApplication
import com.burlakov.petogram.R
import com.rengwuxian.materialedittext.MaterialEditText
import com.rengwuxian.materialedittext.validation.METValidator


/**
 * Класс валидатор эмайл адреса
 */
class EmailValidator(errorMessage: String) : METValidator(errorMessage) {
    /**
     * Проводит валидацию эмайла
     *
     * @param text Эмайл
     * @return true если прошел валидацию, false если не прошел
     */
    override fun isValid(text: CharSequence, isEmpty: Boolean): Boolean {
        return if (!isEmpty) {
            org.apache.commons.validator.routines.EmailValidator.getInstance()
                .isValid(text.toString())
        } else false
    }

    companion object {

        /** Создает проверку валидации для MaterialEditText
         * @param email MaterialEditText
         * @param context Context текущего view
         */
        fun setValidateMaterialEditView(
            email: MaterialEditText,
            context: Context,
        ) {
            val validator =
                EmailValidator(context.getString(R.string.email_error_message))
            email.addValidator(validator)
            email.validate()
            email.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable) {
                    email.validate()
                }
            })
        }

        fun setValidateMaterialEditViewAndButton(
            email: MaterialEditText,
            button: Button,
            context: Context,
            update: Boolean
        ) {
            val validator =
                EmailValidator(context.getString(R.string.email_error_message))
            email.addValidator(validator)
            email.validate()
            email.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable) {
                    if (update) {
                        button.isEnabled =
                            email.validate() && PetogramApplication.user!!.email != email.text.toString()
                    } else button.isEnabled = email.validate()

                }
            })
        }
    }

}