/*
package com.netpaisa.aepsriseinlib.myutils.scroller;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

public class Scroller extends LinearLayout {
    public final a a;
    public RecyclerView b;
    public View c;
    public View d;
    public TextView e;
    public int f;
    public int g;
    public int h;
    public int i;
    public int j;
    public int k;
    public boolean l;
    public c m;
    public d n;

    public Scroller(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public Scroller(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public Scroller(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.a = new a(this);
        this.setClipChildren(false);

        label186: {
            Throwable var10000;
            label190: {
                boolean var10001;
                Scroller var10004;
                Context var10005;
                Scroller var10006;
                Context var10007;
                Scroller var10008;
                Context var10009;
                int var10010;
                try {
                    var10004 = this;
                    var10005 = var1;
                    var10006 = this;
                    var10007 = var1;
                    var10008 = this;
                    var10009 = var1;
                    var10010 = styleable.scroller_scroller_bubbleColor;
                } catch (Throwable var23) {
                    var10000 = var23;
                    var10001 = false;
                    break label190;
                }

                byte var10011 = -1;

                int var27;
                try {
                    var10008.h = var10009.getColor(var10010, var10011);
                    var27 = styleable.scroller_scroller_handleColor;
                } catch (Throwable var22) {
                    var10000 = var22;
                    var10001 = false;
                    break label190;
                }

                byte var28 = -1;

                int var24;
                try {
                    var10006.g = var10007.getColor(var27, var28);
                    var24 = styleable.scroller_scroller__bubbleTextAppearance;
                } catch (Throwable var21) {
                    var10000 = var21;
                    var10001 = false;
                    break label190;
                }

                byte var26 = -1;

                label173:
                try {
                    var10004.i = var10005.getResourceId(var24, var26);
                    break label186;
                } catch (Throwable var20) {
                    var10000 = var20;
                    var10001 = false;
                    break label173;
                }
            }

            var1.recycle();
            throw var10000;
        }

        TypedArray var25;
        (var25 = var1.obtainStyledAttributes(var2, styleable.scroller, attr.scroller_style, 0)).recycle();
        this.k = this.getVisibility();
        this.setViewProvider(new b());
    }

    private void setRecyclerViewPosition(float var1) {
        RecyclerView var2;
        if ((var2 = this.b) != null) {
            int var5 = var2.getAdapter().getItemCount();
            float var10003 = var1;
            var1 = (float)(var5 - 1);
            int var4 = (int)Math.min(Math.max(0.0F, (float)((int)(var10003 * (float)var5))), var1);
            this.b.scrollToPosition(var4);
            TextView var3;
            d var6;
            if ((var6 = this.n) != null && (var3 = this.e) != null) {
                RecyclerItem var7;
                var3.setText(String.valueOf(((var7 = (RecyclerItem)((a.a.a.b)var6).a.get(var4)).getFirstTextValue() != null ? var7.getFirstTextValue() : (var7.getSecondTextValue() != null ? var7.getSecondTextValue() : (var7.getIntId() != -1 ? String.valueOf(var7.getIntId()) : (var7.getStringId() != null ? var7.getStringId() : String.valueOf(var4))))).charAt(0)).toUpperCase(Locale.US));
            }

        }
    }

    public void setViewProvider(c var1) {
        this.removeAllViews();
        this.m = var1;
        var1.a = this;
        b var6;
        b var10000 = var6 = (b)var1;
        var6.d = LayoutInflater.from(var6.a.getContext()).inflate(layout.scroller_default_bubble, this, false);
        this.c = var6.d;
        var10000.e = new View(var6.a.getContext());
        int var2;
        if (var10000.a.b()) {
            var2 = 0;
        } else {
            var2 = var6.a.getContext().getResources().getDimensionPixelSize(dimen.scroller_handle_inset);
        }

        int var3;
        if (!var6.a.b()) {
            var3 = 0;
        } else {
            var3 = var6.a.getContext().getResources().getDimensionPixelSize(dimen.scroller_handle_inset);
        }

        InsetDrawable var4;
        var4 = new InsetDrawable.<init>(ContextCompat.getDrawable(var6.a.getContext(), drawable.scroller_default_handle), var3, var2, var3, var2);
        a.a.a.c.a(var6.e, var4);
        Resources var8 = var6.a.getContext().getResources();
        if (var6.a.b()) {
            var3 = dimen.scroller_handle_clickable_width;
        } else {
            var3 = dimen.scroller_handle_height;
        }

        var2 = var8.getDimensionPixelSize(var3);
        Resources var10 = var6.a.getContext().getResources();
        int var9;
        if (var6.a.b()) {
            var9 = dimen.scroller_handle_height;
        } else {
            var9 = dimen.scroller_handle_clickable_width;
        }

        Scroller var11 = this;
        Scroller var10001 = this;
        Scroller var10002 = this;
        Scroller var10003 = this;
        Scroller var10004 = this;
        b var10005 = var6;
        Scroller var10006 = this;
        b var10007 = var6;
        b var10008 = var6;
        int var5 = var10.getDimensionPixelSize(var9);
        LayoutParams var7;
        var7 = new LayoutParams.<init>(var2, var5);
        var10008.e.setLayoutParams(var7);
        var10006.d = var10007.e;
        var10004.e = (TextView)var10005.d;
        var10002.addView(var10003.c);
        var11.addView(var10001.d);
    }

    public void setRecyclerView(RecyclerView var1) {
        this.b = var1;
        if (var1.getAdapter() instanceof d) {
            this.n = (d)var1.getAdapter();
        }

        var1.addOnScrollListener(this.a);
        this.a();
        var1.setOnHierarchyChangeListener(new a.a.a.a.b(this));
    }

    public void setOrientation(int var1) {
        this.j = var1;
        byte var2;
        if (var1 == 0) {
            var2 = 1;
        } else {
            var2 = 0;
        }

        super.setOrientation(var2);
    }

    public void setBubbleColor(int var1) {
        this.h = var1;
        this.invalidate();
    }

    public void setHandleColor(int var1) {
        this.g = var1;
        this.invalidate();
    }

    public void setBubbleTextAppearance(int var1) {
        this.i = var1;
        this.invalidate();
    }

    public void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
        super.onLayout(var1, var2, var3, var4, var5);
        this.d.setOnTouchListener(new a.a.a.a.c(this));
        b var6;
        int var7 = (int)((var6 = (b)this.m).a.b() ? (float)var6.e.getHeight() / 2.0F - (float)var6.d.getHeight() : (float)var6.e.getWidth() / 2.0F - (float)var6.d.getWidth());
        this.f = var7;
        if ((var7 = this.h) != -1) {
            this.a(this.e, var7);
        }

        if ((var7 = this.g) != -1) {
            this.a(this.d, var7);
        }

        if ((var7 = this.i) != -1) {
            TextViewCompat.setTextAppearance(this.e, var7);
        }

        if (!this.isInEditMode()) {
            this.a.a(this.b);
        }

    }

    public void setVisibility(int var1) {
        this.k = var1;
        this.a();
    }

    public void setScrollerPosition(float var1) {
        View var10000;
        View var10001;
        float var2;
        float var10002;
        if (this.b()) {
            var10001 = this.c;
            var2 = (float)(this.getHeight() - this.c.getHeight());
            var10001.setY(Math.min(Math.max(0.0F, var1 * (float)(this.getHeight() - this.d.getHeight()) + (float)this.f), var2));
            var10000 = this.d;
            var10002 = var1;
            var1 = (float)(this.getHeight() - this.d.getHeight());
            var10000.setY(Math.min(Math.max(0.0F, var10002 * (float)(this.getHeight() - this.d.getHeight())), var1));
        } else {
            var10001 = this.c;
            var2 = (float)(this.getWidth() - this.c.getWidth());
            var10001.setX(Math.min(Math.max(0.0F, var1 * (float)(this.getWidth() - this.d.getWidth()) + (float)this.f), var2));
            var10000 = this.d;
            var10002 = var1;
            var1 = (float)(this.getWidth() - this.d.getWidth());
            var10000.setX(Math.min(Math.max(0.0F, var10002 * (float)(this.getWidth() - this.d.getWidth())), var1));
        }

    }

    public boolean b() {
        return this.j == 1;
    }

    public boolean c() {
        return this.d != null && !this.l && this.b.getChildCount() > 0;
    }

    public c getViewProvider() {
        return this.m;
    }

    public final void a(View var1, int var2) {
        Drawable var3;
        if ((var3 = DrawableCompat.wrap(var1.getBackground())) != null) {
            DrawableCompat.setTint(var3.mutate(), var2);
            var1.setBackground(var3);
        }
    }

    public final void a() {
        if (this.b.getAdapter() != null && this.b.getAdapter().getItemCount() != 0 && this.b.getChildAt(0) != null) {
            boolean var2;
            label28: {
                label27: {
                    int var1;
                    if (this.b()) {
                        var1 = this.b.getChildAt(0).getHeight();
                        if (this.b.getAdapter().getItemCount() * var1 <= this.b.getHeight()) {
                            break label27;
                        }
                    } else {
                        var1 = this.b.getChildAt(0).getWidth();
                        if (this.b.getAdapter().getItemCount() * var1 <= this.b.getWidth()) {
                            break label27;
                        }
                    }

                    var2 = false;
                    break label28;
                }

                var2 = true;
            }

            if (!var2 && this.k == 0) {
                super.setVisibility(0);
                return;
            }
        }

        super.setVisibility(4);
    }
}
*/
