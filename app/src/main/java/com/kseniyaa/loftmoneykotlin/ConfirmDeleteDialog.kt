package com.kseniyaa.loftmoneykotlin

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

@SuppressLint("ValidFragment")
class ConfirmDeleteDialog(var listener: Listener) : DialogFragment() {

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
                .setMessage(R.string.item_del_dialog_msg)
                .setPositiveButton(R.string.item_del_dialog_yes) { _, _ ->
                    listener.onDeleteConfirmed()
                }
                .setNegativeButton(R.string.item_del_dialog_no) { _, _ -> }
                .create()
    }

    interface Listener {
        fun onDeleteConfirmed()
    }
}