package ru.alexbur.dishapp.presentation.dishes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.alexbur.dishapp.R
import ru.alexbur.dishapp.databinding.FragmentDishesBinding
import ru.alexbur.dishapp.di.DishComponent
import ru.alexbur.dishapp.presentation.dishes.adapter.DishesAdapter
import ru.alexbur.dishapp.utils.ViewEvent
import javax.inject.Inject

class DishesFragment : Fragment() {

    companion object {
        private const val TAG = "DishesFragment"
        fun newInstance(): Fragment {
            return DishesFragment()
        }
    }

    @Inject
    lateinit var factory: DishesViewModel.Factory

    private var _binding: FragmentDishesBinding? = null
    private val binding: FragmentDishesBinding get() = requireNotNull(_binding)
    private var adapter: DishesAdapter? = null
    private val viewModel by viewModels<DishesViewModel> { factory }

    override fun onAttach(context: Context) {
        DishComponent.getComponent().inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
    }

    private fun setupView() {
        adapter = DishesAdapter(
            clickCheckBox = { key, isChecked ->
                viewModel.obtainAction(DishesAction.ClickCheckBox(key, isChecked))
            },
            clickItem = { key ->
                viewModel.obtainAction(DishesAction.ClickItem(key))
            }
        )
        binding.rvDishes.adapter = adapter
        binding.btnRemove.setOnClickListener {
            viewModel.obtainAction(DishesAction.RemoveClick)
        }
    }

    private fun observeViewModel() {
        viewModel.viewState.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach { state ->
            renderState(state)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.viewEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach { event ->
            when (event) {
                is ViewEvent.NavigateTo -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.mainContainer, event.screen.destination)
                        .addToBackStack(TAG)
                        .commit()
                }

                is ViewEvent.ShowSnackBar -> {
                    // показываем снек
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun renderState(state: DishesViewState) = with(binding) {
        progressBar.isVisible = state.isLoading
        adapter?.submitList(state.dishes)
        btnRemove.isEnabled = state.isEnableRemoveButton
        state.errorText?.let {
            tvErrorText.text = it
            tvErrorText.isVisible = true
        } ?: run {
            if (state.dishes.isEmpty()) {
                tvErrorText.isVisible = true
                tvErrorText.setText(R.string.empty_data)
            } else {
                tvErrorText.isVisible = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        _binding = null
    }
}