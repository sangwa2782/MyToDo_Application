package com.ajayam.mytodo

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ajayam.mytodo.data.entity.Todo
import com.ajayam.mytodo.databinding.FragmentEditorTodoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EditorTodoFragment(var todo: Todo?) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEditorTodoBinding

    private var editorToolDialogListener: OnEditorTodoListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditorTodoBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etTodoName.addTextChangedListener(textWatcher)

        if (todo != null) {
            val editable = Editable.Factory.getInstance()
            binding.etTodoName.text = editable.newEditable(todo?.todoName)
        }

        binding.btnCreatedTodo.setOnClickListener{
            val etTodoName = binding.etTodoName.text.toString().trim()

            if (todo != null) {
                editorToolDialogListener?.onTodoValue(todo?.id, etTodoName)
            } else {
                editorToolDialogListener?.onTodoValue(null, etTodoName)
            }

            dialog?.dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.editorToolDialogListener = (activity as MainActivity).editorTodoFragment
    }

    override fun onDetach() {
        super.onDetach()
        this.editorToolDialogListener = null
    }

    private val textWatcher = object  : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
            val etTodoNameValue = binding.etTodoName.text.toString().trim()

            binding.btnCreatedTodo.isEnabled = etTodoNameValue.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }


    interface OnEditorTodoListener {
        fun onTodoValue(id: Int? = null, todoName: String)
    }

}