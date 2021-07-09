/*
package com.netpaisa.aepsriseinlib.myutils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextWatcher;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.netpaisa.aepsriseinlib.R;
import com.netpaisa.aepsriseinlib.myutils.scroller.Scroller;

import java.util.List;

public class RecyclerDialog extends Dialog {
    public boolean a;
    public boolean b;
    public boolean c;
    public Sort d;
    public List<RecyclerItem> e;
    public List<RecyclerItem> f;
    public Context g;
    public boolean h;
    public boolean i;
    public b j;
    public a k;
    public Scroller l;
    public RelativeLayout m;
    public RelativeLayout n;
    public TextWatcher o;

    public RecyclerDialog(@NonNull Context var1, List<RecyclerItem> var2) {
        super(var1);
        this.a = true;
        this.b = false;
        this.c = false;
        this.d = Sort.FIRST_TEXT_VALUE;
        this.h = true;
        this.i = true;
        this.o = new d(this);
        this.e = var2;
        this.f = var2;
        this.g = var1;
    }

    public RecyclerDialog(@NonNull Context var1, List<RecyclerItem> var2, boolean var3) {
        this(var1, var2, style.ThemeDialog);
    }

    public RecyclerDialog(@NonNull Context var1, List<RecyclerItem> var2, @StyleRes int var3) {
        super(var1, var3);
        this.a = true;
        this.b = false;
        this.c = false;
        this.d = Sort.FIRST_TEXT_VALUE;
        this.h = true;
        this.i = true;
        this.o = new d(this);
        this.e = var2;
        this.f = var2;
        this.g = var1;
    }

    public RecyclerDialog(@NonNull Context var1) {
        super(var1);
        this.a = true;
        this.b = false;
        this.c = false;
        this.d = Sort.FIRST_TEXT_VALUE;
        this.h = true;
        this.i = true;
        this.o = new d(this);
        this.g = var1;
    }

    public boolean isSortList() {
        return this.b;
    }

    public void setSortList(Sort var1, boolean var2) {
        this.d = var1;
        this.b = var2;
    }

    public void setTitle(String var1) {
        if (var1 != null && !var1.isEmpty()) {
            TextView var2;
            if ((var2 = (TextView)this.findViewById(R.id.txtTitle)) != null) {
                var2.setText(var1);
            }

        }
    }

    public boolean isSearchable() {
        return this.h;
    }
*/
