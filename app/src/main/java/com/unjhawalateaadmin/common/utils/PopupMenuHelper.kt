package com.unjhawalateaadmin.common.utils

import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.data.model.ModuleInfo

object PopupMenuHelper {
    fun showPopupMenu(
        mContext: Context?,
        view: View?,
        list: List<ModuleInfo>,
        dialogIdentifier: Int,
        listener: SelectItemListener
    ) {
        val popupMenu = PopupMenu(
            mContext!!, view!!
        )
        for (i in list.indices)
            popupMenu.menu.add(Menu.NONE, i, i, list[i].name)
        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            val position = menuItem.itemId
            listener.onSelectItem(position, dialogIdentifier,0)
            false
        }
        popupMenu.show()
    }
}
