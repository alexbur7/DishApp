package ru.alexbur.dishapp.presentation.dishes.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.alexbur.dishapp.databinding.FragmentDishDetailBinding
import ru.alexbur.dishapp.di.DishComponent
import ru.alexbur.dishapp.utils.ViewEvent
import javax.inject.Inject

class DishDetailFragment : Fragment() {

    companion object {
        private const val ID_KEY = "id_key"

        fun newInstance(id: String): DishDetailFragment {
            return DishDetailFragment().apply {
                arguments = bundleOf(ID_KEY to id)
            }
        }
    }

    @Inject
    lateinit var factory: DishDetailViewModel.Factory

    private var _binding: FragmentDishDetailBinding? = null
    private val binding: FragmentDishDetailBinding get() = requireNotNull(_binding)
    private val viewModel by viewModels<DishDetailViewModel> {
        DishDetailViewModel.create(requireArguments().getString(ID_KEY).orEmpty(), factory)
    }

    override fun onAttach(context: Context) {
        DishComponent.getComponent().inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.obtainAction(DishDetailAction.ExitClick)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.viewState.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach { state ->
            renderState(state)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.viewEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach { event ->
            when (event) {
                is ViewEvent.PopBackStack -> {
                    parentFragmentManager.popBackStack()
                }

                is ViewEvent.ShowSnackBar -> {
                    // показать снек
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun renderState(state: DishDetailViewState) = with(binding) {
        progressBar.isVisible = state.isLoading
        val content = state.content ?: return@with
        tvDishName.text = content.name
        tvDishDescription.text = content.description
        tvDishPrice.text = content.price
        Glide.with(requireContext()).load(content.pictureUrl).into(ivPicture)
    }
}