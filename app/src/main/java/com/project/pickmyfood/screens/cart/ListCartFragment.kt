package com.project.pickmyfood.screens.cart

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.pickmyfood.R
import com.project.pickmyfood.container.MyApplication
import com.project.pickmyfood.data.order.OrderViewModel
import com.project.pickmyfood.data.order.adapter.OrderRecycledAdapter
import kotlinx.android.synthetic.main.fragment_list_cart.*
import javax.inject.Inject


class ListCartFragment : Fragment() {
    @Inject
    lateinit var orderViewModel:OrderViewModel
//    lateinit var checkOutViewModel: CheckOutViewModel
    lateinit var orderRecycledAdapter: OrderRecycledAdapter
    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
        sharedPreferences = activity?.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val orderID = sharedPreferences?.getString( // for get sharedPreferences
//            getString(R.string.order_key),
//            getString(R.string.default_value)
//        )
        orderListRecycleView.layoutManager = LinearLayoutManager(this.context)

        val orderID = arguments?.getString("orderID")
        orderIDText.text = orderID
        orderViewModel.getOrderByID(orderID.toString())
//        orderListRecycleView.layoutManager = LinearLayoutManager(activity)
        orderViewModel.order?.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                orderRecycledAdapter= OrderRecycledAdapter(it.soldItems,totalOrderPrice)
                orderListRecycleView.adapter = orderRecycledAdapter
                println("SoldItems ${it.soldItems[0].productName}")
                orderDateText.text = it.orderCreated
                storeIDText.text = it.storeID
            }
        })

        println("order ID ${orderIDText.toString()}")

//        println("Response data ${checkOutViewModel.checkOutResponseData}")

    }
}