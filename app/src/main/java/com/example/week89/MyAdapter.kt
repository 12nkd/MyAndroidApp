package com.example.week89
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter (private val data:ArrayList<Context> )
    : RecyclerView.Adapter<MyAdapter.ViewHolder>()
{
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        //存View原件
        private val tvCourse:TextView=view.findViewById(R.id.tvCourse)
        private val imgDelte:ImageView=view.findViewById(R.id.imgDelete)
        //连接资料view
        fun bind(item:Context,clickListener: (Context)->unit){
            tvCourse.text=item.course
            imgDelte.setOnClickListener {
                //呼叫clicklistener回传删除资料
                clickListener.invoke(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.adapter_row,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position]){
            item->
            data.remove(item)
            notifyDataSetChanged()
        }
    }

}