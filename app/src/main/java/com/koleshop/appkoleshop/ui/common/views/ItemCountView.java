package com.koleshop.appkoleshop.ui.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.koleshop.appkoleshop.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gundeep on 03/03/16.
 */
public class ItemCountView extends LinearLayout {

    public static final int VIEW_FLIPPER_COUNT = 0;
    public static final int VIEW_FLIPPER_OUT_OF_STOCK = 1;
    public static final int VIEW_FLIPPER_ADD_TO_CART = 2;

    @Bind(R.id.vf_item_count)
    ViewFlipper viewFlipper;
    @Bind(R.id.button_minus_item_count)
    Button buttonMinus;
    @Bind(R.id.tv_count_item_count)
    TextView textViewItemCount;
    @Bind(R.id.button_plus_item_count)
    Button buttonPlus;
    @Bind(R.id.button_add_to_cart_view_item_count)
    Button buttonAddToCart;

    boolean showZeroCount;
    boolean outOfStock;
    int count;
    Context mContext;
    private ItemCountListener mListener;

    public ItemCountView(Context context) {
        super(context);
        init();
    }

    public ItemCountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ItemCountView,
                0, 0);

        try {
            showZeroCount = a.getBoolean(R.styleable.ItemCountView_showZeroCount, false);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.view_item_count, null);
        addView(view);
        initializeViews(view);
    }

    private void initializeViews(View view) {
        ButterKnife.bind(this, view);
        refreshThisLayout();
        buttonPlus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemCountPlusClicked();
                }
            }
        });
        buttonMinus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemCountMinusClicked();
                }
            }
        });
        buttonAddToCart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemCountPlusClicked();
                }
            }
        });
    }

    private void refreshThisLayout() {
        if(outOfStock) {
            viewFlipper.setDisplayedChild(VIEW_FLIPPER_OUT_OF_STOCK);
        } else if(!showZeroCount && count == 0) {
            viewFlipper.setDisplayedChild(VIEW_FLIPPER_ADD_TO_CART);
        } else {
            textViewItemCount.setText(count+"");
            viewFlipper.setDisplayedChild(VIEW_FLIPPER_COUNT);
        }
    }

    public void setItemCountListener(ItemCountListener itemCountListener) {
        this.mListener = itemCountListener;
    }

    public boolean isOutOfStock() {
        return outOfStock;
    }

    public void setOutOfStock(boolean outOfStock) {
        this.outOfStock = outOfStock;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        refreshThisLayout();
    }

    public boolean isShowZeroCount() {
        return showZeroCount;
    }

    public void setShowZeroCount(boolean showZeroCount) {
        this.showZeroCount = showZeroCount;
    }

    public interface ItemCountListener {
        void onItemCountPlusClicked();
        void onItemCountMinusClicked();
    }
}
