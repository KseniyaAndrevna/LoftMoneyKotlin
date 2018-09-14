package com.kseniyaa.loftmoneykotlin

import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

class ConfirmDeleteDialog : DialogFragment() {

    private var listener: Listener? = null

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
                .setMessage(R.string.item_del_dialog_msg)
                .setPositiveButton(R.string.item_del_dialog_yes) { dialog, which ->
                    listener?.onDeleteConfirmed()
                }
                .setNegativeButton(R.string.item_del_dialog_no) { dialog, which -> }
                .create()
    }

    interface Listener {
        fun onDeleteConfirmed()
    }
}