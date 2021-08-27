package cu.cujae.gilsoft.tykeprof.app.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.adapter.Bonus_Adapter;
import cu.cujae.gilsoft.tykeprof.adapter.Clue_Adapter;
import cu.cujae.gilsoft.tykeprof.adapter.answer.AnswerLink_Adapter;
import cu.cujae.gilsoft.tykeprof.adapter.answer.AnswerOrder_Adapter;
import cu.cujae.gilsoft.tykeprof.adapter.answer.AnswerOther_Adapter;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Subject;
import cu.cujae.gilsoft.tykeprof.data.model.Answer_Model;
import cu.cujae.gilsoft.tykeprof.data.model.Bonus_Model;
import cu.cujae.gilsoft.tykeprof.data.model.Clue_Model;
import cu.cujae.gilsoft.tykeprof.databinding.DialogAddBonusBinding;
import cu.cujae.gilsoft.tykeprof.databinding.DialogAddClueBinding;
import cu.cujae.gilsoft.tykeprof.databinding.DialogAddLinkAnswerBinding;
import cu.cujae.gilsoft.tykeprof.databinding.DialogAddOrderAnswerBinding;
import cu.cujae.gilsoft.tykeprof.databinding.DialogAddOtherAnswerBinding;
import cu.cujae.gilsoft.tykeprof.databinding.NewQuestion2stepFragmentBinding;
import cu.cujae.gilsoft.tykeprof.databinding.NewQuestion3stepFragmentBinding;
import cu.cujae.gilsoft.tykeprof.databinding.NewQuestion4stepFragmentBinding;
import cu.cujae.gilsoft.tykeprof.databinding.NewQuestion5stepFragmentBinding;
import cu.cujae.gilsoft.tykeprof.databinding.NewQuestionFragmentBinding;
import cu.cujae.gilsoft.tykeprof.util.ToastHelper;

public class NewQuestionFragment extends Fragment {

    private NewQuestionFragmentBinding newQuestionFragmentBinding;
    private NewQuestion2stepFragmentBinding newQuestion2stepFragmentBinding;
    private NewQuestion3stepFragmentBinding newQuestion3stepFragmentBinding;
    private NewQuestion4stepFragmentBinding newQuestion4stepFragmentBinding;
    private NewQuestion5stepFragmentBinding newQuestion5stepFragmentBinding;

    private DialogAddClueBinding dialogAddClueBinding;
    private DialogAddBonusBinding dialogAddBonusBinding;
    private DialogAddOtherAnswerBinding dialogAddOtherAnswerBinding;
    private DialogAddOrderAnswerBinding dialogAddOrderAnswerBinding;
    private DialogAddLinkAnswerBinding dialogAddLinkAnswerBinding;

    private NavController newQuestionNavController;
    private NavController mainNavController;

    private Clue_Adapter clue_adapter;
    private Bonus_Adapter bonus_adapter;
    private AnswerLink_Adapter answerLinkAdapter;
    private AnswerOrder_Adapter answerOrderAdapter;
    private AnswerOther_Adapter answerOtherAdapter;

    private ArrayList<Clue_Type> clueTypeList;
    private ArrayAdapter<Clue_Type> clueTypeAdapter;

    private int step;
    private String questionType;
    private RecyclerView recyclerAnswerList;

    public static NewQuestionFragment newInstance() {
        return new NewQuestionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = null;
        newQuestionFragmentBinding = NewQuestionFragmentBinding.inflate(inflater, container, false);
        newQuestion2stepFragmentBinding = NewQuestion2stepFragmentBinding.inflate(inflater, container, false);
        newQuestion3stepFragmentBinding = NewQuestion3stepFragmentBinding.inflate(inflater, container, false);
        newQuestion4stepFragmentBinding = NewQuestion4stepFragmentBinding.inflate(inflater, container, false);
        newQuestion5stepFragmentBinding = NewQuestion5stepFragmentBinding.inflate(inflater, container, false);
        step = getArguments().getInt("stepNumber");

        //BRINDAR LA VISTA CORRESPONDIENTE AL PASO DONDE SE ENCUENTRE EL USUARIO
        switch (step) {
            case 1:
                root = newQuestionFragmentBinding.getRoot();
                break;
            case 2:
                root = newQuestion2stepFragmentBinding.getRoot();
                break;
            case 3:
                root = newQuestion3stepFragmentBinding.getRoot();
                break;
            case 4:
                root = newQuestion4stepFragmentBinding.getRoot();
                break;
            case 5:
                root = newQuestion5stepFragmentBinding.getRoot();
                break;
        }
        return root;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //SOLICITAR AL SERVIDOR TODAS LAS ASIGNATURAS
        HomeNewQuestionFragment.homeNewQuestionViewModel.getLiveSubjectList().observe(getViewLifecycleOwner(), subjectList -> {
            ArrayAdapter<Subject> subjectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, subjectList);
            newQuestionFragmentBinding.autoCompleteSujectOfNewQuestion.setAdapter(subjectAdapter);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //CONTROLADORES DE NAVEGACIÓN
        newQuestionNavController = Navigation.findNavController(getActivity(), R.id.newQuestionNavHostFragment);
        mainNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_container);

        //INICIALIZAR DATOS
        init(step);

        //CHEQUEAR CAMBIOS EN EL TIPO DE PREGUNTA
        newQuestion2stepFragmentBinding.autoCompleteQuestionTypeOfNewQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Question_Type question_Type = (Question_Type) newQuestion2stepFragmentBinding.autoCompleteQuestionTypeOfNewQuestion.getAdapter().getItem(position);
                if (!question_Type.getType().equalsIgnoreCase(questionType))
                    changeAdapter(question_Type.getType());
            }
        });

        // AÑADIR UNA PREGUNTA
        newQuestion2stepFragmentBinding.viewAddAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v);
            }
        });

        // AÑADIR UNA PISTA
        newQuestion3stepFragmentBinding.viewAddClueOfNewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClue(v);
            }
        });

        // AÑADIR UNA BONIFICACIÓN
        newQuestion4stepFragmentBinding.viewAddBonusOfNewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBonus(v);
            }
        });

        // ESCUCHAR CAMBIOS EN EL SLIDER CORRESPONDIENTE AL TIEMPO PARA RESPONDER LA PREGUNTA
        newQuestionFragmentBinding.sliderAnswerTime.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                newQuestionFragmentBinding.sliderAnswerTime.setValue((int) slider.getValue());
                newQuestionFragmentBinding.textViewAnswerTime.setText(getString(R.string.answer_time) + " " + (int) slider.getValue());
            }
        });

        //ESCUCHAR CAMBIOS EN EL SLIDER CORRESPONDIENTE A LA PUNTUACIÓN PARA CONTESTAR LA PREGUNTA
        newQuestionFragmentBinding.sliderAnswerPoints.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                newQuestionFragmentBinding.sliderAnswerPoints.setValue((int) slider.getValue());
                newQuestionFragmentBinding.textViewAnswerPoints.setText(getString(R.string.answer_point) + " " + (int) slider.getValue());
            }
        });

        //IR AL PASO 2
        newQuestionFragmentBinding.floatingActionButtonNext2Step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionTitle = newQuestionFragmentBinding.editTextQuestionTitle.getText().toString();
                String questionSubject = newQuestionFragmentBinding.autoCompleteSujectOfNewQuestion.getText().toString();
                if (questionTitle.isEmpty() || questionSubject.isEmpty()) {
                    if (questionTitle.isEmpty())
                        newQuestionFragmentBinding.textInputLayoutQuestionTitle.setError(getString(R.string.required));
                    if (questionSubject.isEmpty())
                        newQuestionFragmentBinding.textInputLayouSubjectOfNewQuestion.setError(getString(R.string.required));
                } else {
                    HomeNewQuestionFragment.questionModel.setQuestionTitle(questionTitle);
                    HomeNewQuestionFragment.questionModel.setSubject_name(questionSubject);
                    HomeNewQuestionFragment.questionModel.setTime_second((int) newQuestionFragmentBinding.sliderAnswerTime.getValue());
                    HomeNewQuestionFragment.questionModel.setPunctuation((int) newQuestionFragmentBinding.sliderAnswerPoints.getValue());
                    newQuestionNavController.navigate(R.id.go_newQuestion2Step);
                    HomeNewQuestionFragment.stepViewNewQuestion.go(1, true);
                }

            }
        });

        //REGRESAR AL PASO 1
        newQuestion2stepFragmentBinding.floatingActionButtonBack1Step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionType != null) {
                    if (!questionType.isEmpty()) {
                        HomeNewQuestionFragment.questionModel.setQuestionType(questionType);
                        addAnswerList(questionType);
                    }
                }
                newQuestionNavController.navigate(R.id.go_newQuestionBack1Step);
                HomeNewQuestionFragment.stepViewNewQuestion.go(0, true);
            }
        });

        //IR AL PASO 3
        newQuestion2stepFragmentBinding.floatingActionButtonNext3Step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionType = newQuestion2stepFragmentBinding.autoCompleteQuestionTypeOfNewQuestion.getText().toString();
                if (questionType.isEmpty() || HomeNewQuestionFragment.questionModel.getAnswerModelList().isEmpty()) {
                    if (questionType.isEmpty())
                        newQuestion2stepFragmentBinding.textInputLayoutQuestionTypeOfNewQuestion.setError(getString(R.string.required));
                    if (HomeNewQuestionFragment.questionModel.getAnswerModelList().isEmpty())
                        ToastHelper.showCustomToast(getActivity(), "error", getString(R.string.should_add_answer));
                } else {
                    HomeNewQuestionFragment.questionModel.setQuestionType(questionType);
                    addAnswerList(questionType);
                    newQuestionNavController.navigate(R.id.go_newQuestion3Step);
                    HomeNewQuestionFragment.stepViewNewQuestion.go(2, true);
                }
            }
        });

        //REGRESAR AL PASO 2
        newQuestion3stepFragmentBinding.floatingActionButtonBack2Step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newQuestionNavController.navigate(R.id.go_newQuestionBack2Step);
                HomeNewQuestionFragment.stepViewNewQuestion.go(1, true);
            }
        });

        //IR AL PASO 4
        newQuestion3stepFragmentBinding.floatingActionButtonNext4Step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeNewQuestionFragment.questionModel.setClueModelList(clue_adapter.clueModels);
                newQuestionNavController.navigate(R.id.go_newQuestion4Step);
                HomeNewQuestionFragment.stepViewNewQuestion.go(3, true);
            }
        });

        //REGRESAR AL PASO 3
        newQuestion4stepFragmentBinding.floatingActionButtonBack3Step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newQuestionNavController.navigate(R.id.go_newQuestionBack3Step);
                HomeNewQuestionFragment.stepViewNewQuestion.go(2, true);
            }
        });

        //IR AL PASO 5
        newQuestion4stepFragmentBinding.floatingActionButtonNext5Step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeNewQuestionFragment.questionModel.setBonusList(bonus_adapter.bonusModels);
                newQuestionNavController.navigate(R.id.go_newQuestion5Step);
                HomeNewQuestionFragment.stepViewNewQuestion.go(4, true);
            }
        });

        //REGRESAR AL PASO 4
        newQuestion5stepFragmentBinding.floatingActionButtonBack4Step.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newQuestionNavController.navigate(R.id.go_newQuestionBack4Step);
                HomeNewQuestionFragment.stepViewNewQuestion.go(3, true);
            }
        });

        //FINALIZAR LA CREACIÓN DE UNA NUEVA PREGUNTA
        newQuestion5stepFragmentBinding.materialButtonFinishNewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeNewQuestionFragment.homeNewQuestionViewModel.saveQuestion(HomeNewQuestionFragment.questionModel);
                mainNavController.popBackStack(R.id.nav_questionFragment, false);
            }
        });
    }

    //INICIAR COMPONENTES VISUALES EN DEPENDENCIA DEL PASO DONDE SE ENCUENTRE EL USUARIO
    @SuppressLint("SetTextI18n")
    public void init(int step) {

        switch (step) {
            case 1:
                try {
                    newQuestionFragmentBinding.editTextQuestionTitle.setText(HomeNewQuestionFragment.questionModel.getQuestionTitle());
                    newQuestionFragmentBinding.textViewAnswerTime.setText(getString(R.string.answer_time) + " " + HomeNewQuestionFragment.questionModel.getTime_second());
                    newQuestionFragmentBinding.sliderAnswerTime.setValue(HomeNewQuestionFragment.questionModel.getTime_second());
                    newQuestionFragmentBinding.textViewAnswerPoints.setText(getString(R.string.answer_point) + " " + HomeNewQuestionFragment.questionModel.getPunctuation());
                    newQuestionFragmentBinding.sliderAnswerPoints.setValue(HomeNewQuestionFragment.questionModel.getPunctuation());
                } catch (Exception e) {
                    newQuestionFragmentBinding.editTextQuestionTitle.setText("");
                    newQuestionFragmentBinding.textViewAnswerTime.setText(R.string.answer_time);
                    newQuestionFragmentBinding.sliderAnswerTime.setValue(10);
                    newQuestionFragmentBinding.textViewAnswerPoints.setText(getString(R.string.answer_point));
                    newQuestionFragmentBinding.sliderAnswerPoints.setValue(1);
                }
                try {
                    newQuestionFragmentBinding.autoCompleteSujectOfNewQuestion.setText(HomeNewQuestionFragment.questionModel.getSubject_name());
                } catch (Exception e) {
                    newQuestionFragmentBinding.autoCompleteSujectOfNewQuestion.setText("");
                }
                break;
            case 2:
                recyclerAnswerList = newQuestion2stepFragmentBinding.recyclerViewAnswersOfNewQuestion;
                recyclerAnswerList.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerAnswerList.setHasFixedSize(true);
                try {
                    newQuestion2stepFragmentBinding.autoCompleteQuestionTypeOfNewQuestion.setText(HomeNewQuestionFragment.questionModel.getQuestionType());
                    initAdapter(HomeNewQuestionFragment.questionModel.getQuestionType());
                } catch (Exception e) {
                    newQuestion2stepFragmentBinding.autoCompleteQuestionTypeOfNewQuestion.setText("");
                }
                ArrayList<Question_Type> question_typeArrayList = (ArrayList<Question_Type>) HomeNewQuestionFragment.homeNewQuestionViewModel.getQuestionTypeList();
                ArrayAdapter<Question_Type> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, question_typeArrayList);
                newQuestion2stepFragmentBinding.autoCompleteQuestionTypeOfNewQuestion.setAdapter(arrayAdapter);
                break;
            case 3:
                clueTypeList = (ArrayList<Clue_Type>) HomeNewQuestionFragment.homeNewQuestionViewModel.getClue_typeList();
                clueTypeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, clueTypeList);
                RecyclerView recyclerViewClues = newQuestion3stepFragmentBinding.recyclerViewCluesOfNewQuestion;
                recyclerViewClues.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewClues.setHasFixedSize(true);
                clue_adapter = new Clue_Adapter(HomeNewQuestionFragment.questionModel.getClueModelList(), getActivity());
                recyclerViewClues.setAdapter(clue_adapter);
                break;
            case 4:
                RecyclerView recyclerViewBonus = newQuestion4stepFragmentBinding.recyclerViewBonusOfNewQuestion;
                recyclerViewBonus.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewBonus.setHasFixedSize(true);
                bonus_adapter = new Bonus_Adapter(HomeNewQuestionFragment.questionModel.getBonusList(), getActivity());
                recyclerViewBonus.setAdapter(bonus_adapter);
                break;
            case 5:
                newQuestion5stepFragmentBinding.textViewQuestionTitleFinish.setText(getString(R.string.question_title) + " " + HomeNewQuestionFragment.questionModel.getQuestionTitle());
                newQuestion5stepFragmentBinding.textViewAnswerTimeFinish.setText(getString(R.string.answer_time) + " " + HomeNewQuestionFragment.questionModel.getTime_second());
                newQuestion5stepFragmentBinding.textViewScoreFinish.setText(getString(R.string.punctuation) + " " + HomeNewQuestionFragment.questionModel.getPunctuation());
                newQuestion5stepFragmentBinding.textViewQuestionTypeFinish.setText(getString(R.string.question_type) + ": " + HomeNewQuestionFragment.questionModel.getQuestionType());
                newQuestion5stepFragmentBinding.textViewSubjectFinish.setText(getString(R.string.subject) + ": " + HomeNewQuestionFragment.questionModel.getSubject_name());
                newQuestion5stepFragmentBinding.textViewAnswerCantFinish.setText(getString(R.string.answer_cant) + " " + HomeNewQuestionFragment.questionModel.getAnswerModelList().size());
                if (!HomeNewQuestionFragment.questionModel.getClueModelList().isEmpty())
                    newQuestion5stepFragmentBinding.textViewClueFinish.setText(getString(R.string.clue) + ": " + getString(R.string.yes));
                else
                    newQuestion5stepFragmentBinding.textViewClueFinish.setText(getString(R.string.clue) + ": No");
                if (!HomeNewQuestionFragment.questionModel.getBonusList().isEmpty())
                    newQuestion5stepFragmentBinding.textViewBonusFinish.setText(getString(R.string.bonus) + " " + getString(R.string.yes));
                else
                    newQuestion5stepFragmentBinding.textViewBonusFinish.setText(getString(R.string.bonus) + " No");
                newQuestion5stepFragmentBinding.textViewClueCantFinish.setText(getString(R.string.clue_cant) + " " + HomeNewQuestionFragment.questionModel.getClueModelList().size());
                newQuestion5stepFragmentBinding.textViewBonusCantFinish.setText(getString(R.string.bonus_cant) + " " + HomeNewQuestionFragment.questionModel.getBonusList().size());
                break;
        }

    }

    // AÑADIR UNA PISTA A LA PREGUNTA
    public void addClue(View view) {

        dialogAddClueBinding = DialogAddClueBinding.inflate(getLayoutInflater(), null, false);
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.add_clue)
                .setPositiveButton(R.string.accept, null)
                .setNegativeButton(getString(R.string.cancel), null)
                .setView(dialogAddClueBinding.getRoot())
                .show();

        dialogAddClueBinding.autoCompleteClueTypeOfClue.setAdapter(clueTypeAdapter);

        Button buttonPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        buttonPos.setOnClickListener(v1 -> {
            String stringClueName = dialogAddClueBinding.editTextClueName.getText().toString();
            String stringClueTypeName = dialogAddClueBinding.autoCompleteClueTypeOfClue.getText().toString();
            if (stringClueName.isEmpty() || stringClueTypeName.isEmpty()) {
                if (stringClueName.isEmpty())
                    dialogAddClueBinding.textInputLayoutClueName.setError(getString(R.string.required));
                if (stringClueTypeName.isEmpty())
                    dialogAddClueBinding.textInputLayoutClueTypeOfClue.setError(getString(R.string.required));
            } else {
                clue_adapter.clueModels.add(new Clue_Model(stringClueName, stringClueTypeName));
                clue_adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        Button buttonNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        buttonNeg.setOnClickListener(v12 -> dialog.dismiss());
    }

    // AÑADIR UNA BONIFICACIÓN A LA PREGUNTA
    public void addBonus(View view) {
        dialogAddBonusBinding = DialogAddBonusBinding.inflate(getLayoutInflater(), null, false);
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.add_bonus)
                .setPositiveButton(R.string.accept, null)
                .setNegativeButton(getString(R.string.cancel), null)
                .setView(dialogAddBonusBinding.getRoot())
                .show();

        //ESCUCHAR CAMBIOS EN EL SLIDER CORRESPONDIENTE A LA BONIFICACIÓN
        dialogAddBonusBinding.sliderBonus.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                dialogAddBonusBinding.sliderBonus.setValue((int) slider.getValue());
                dialogAddBonusBinding.textViewBonus.setText(getString(R.string.bonus) + " " + (int) slider.getValue());
            }
        });

        //ESCUCHAR CAMBIOS EN EL SLIDER CORRESPONDIENTE AL TIEMPO MENOR
        dialogAddBonusBinding.sliderTimeLess.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                dialogAddBonusBinding.sliderTimeLess.setValue((int) slider.getValue());
                dialogAddBonusBinding.textViewTimeLess.setText(getString(R.string.time_less) + ": " + (int) slider.getValue());
            }
        });

        Button buttonPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        buttonPos.setOnClickListener(v1 -> {
            bonus_adapter.bonusModels.add(new Bonus_Model((int) dialogAddBonusBinding.sliderBonus.getValue(), (long) dialogAddBonusBinding.sliderTimeLess.getValue()));
            bonus_adapter.notifyDataSetChanged();
            dialog.dismiss();

        });
        Button buttonNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        buttonNeg.setOnClickListener(v12 -> dialog.dismiss());
    }

    // EN DEPENDENCIA DEL TIPO DE PREGUNTA SE MUESTRA EL DIÁLOGO CORRESPONDIENTE PARA AÑADIR UNA RESPUESTA
    public void checkAnswer(View view) {
        questionType = newQuestion2stepFragmentBinding.autoCompleteQuestionTypeOfNewQuestion.getText().toString();
        if (!questionType.isEmpty()) {
            if (questionType.equalsIgnoreCase("Enlazar"))
                addAnswerOfLinkQuestion();
            else if (questionType.equalsIgnoreCase("Ordenar"))
                addAnswerOfOrderQuestion();
            else
                addAnswerOfOtherQuestion();
        } else
            newQuestion2stepFragmentBinding.textInputLayoutQuestionTypeOfNewQuestion.setError(getString(R.string.required));
    }

    // AÑADIR UNA RESPUESTA A UNA PREGUNTA DE TIPO ENLAZA
    public void addAnswerOfLinkQuestion() {

        dialogAddLinkAnswerBinding = DialogAddLinkAnswerBinding.inflate(getLayoutInflater(), null, false);
        dialogAddLinkAnswerBinding.sliderPunctuationOfLinkQuestion.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                dialogAddLinkAnswerBinding.sliderPunctuationOfLinkQuestion.setValue((int) slider.getValue());
            }
        });

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.create_answer)
                .setPositiveButton(R.string.accept, null)
                .setNegativeButton(getString(R.string.cancel), null)
                .setView(dialogAddLinkAnswerBinding.getRoot())
                .show();

        Button buttonPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        buttonPos.setOnClickListener(v1 -> {
            String stringQuestion = dialogAddLinkAnswerBinding.editTextLinkQuestion.getText().toString();
            String stringAnswer = dialogAddLinkAnswerBinding.editTextLinkAnswer.getText().toString();
            if (stringQuestion.isEmpty() || stringAnswer.isEmpty()) {
                if (stringQuestion.isEmpty())
                    dialogAddLinkAnswerBinding.textInputLayoutLinkQuestion.setError(getString(R.string.required));
                if (stringAnswer.isEmpty())
                    dialogAddLinkAnswerBinding.textInputLayoutLinkAnswer.setError(getString(R.string.required));
            } else {
                Answer_Model answer_model = new Answer_Model();
                answer_model.setQuestion(stringQuestion);
                answer_model.setAnswer(stringAnswer);
                answer_model.setPunctuation((int) dialogAddLinkAnswerBinding.sliderPunctuationOfLinkQuestion.getValue());
                answerLinkAdapter.answerModels.add(answer_model);
                answerLinkAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        Button buttonNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        buttonNeg.setOnClickListener(v12 -> dialog.dismiss());

    }

    // AÑADIR UNA RESPUESTA A UNA PREGUNTA DE TIPO ORDENA
    public void addAnswerOfOrderQuestion() {

        dialogAddOrderAnswerBinding = DialogAddOrderAnswerBinding.inflate(getLayoutInflater(), null, false);

        dialogAddOrderAnswerBinding.sliderPunctuationOfOrderAnswer.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                dialogAddOrderAnswerBinding.sliderPunctuationOfOrderAnswer.setValue((int) slider.getValue());
            }
        });

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.create_answer)
                .setPositiveButton(R.string.accept, null)
                .setNegativeButton(getString(R.string.cancel), null)
                .setView(dialogAddOrderAnswerBinding.getRoot())
                .show();

        Button buttonPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        buttonPos.setOnClickListener(v1 -> {
            String stringOrder = dialogAddOrderAnswerBinding.editTextOrder.getText().toString();
            String stringAnswer = dialogAddOrderAnswerBinding.editTextOrderAnswerText.getText().toString();
            if (stringOrder.isEmpty() || stringAnswer.isEmpty()) {
                if (stringOrder.isEmpty())
                    dialogAddOrderAnswerBinding.textInputLayoutOrder.setError(getString(R.string.required));
                if (stringAnswer.isEmpty())
                    dialogAddOrderAnswerBinding.textInputLayoutOrderAnswerText.setError(getString(R.string.required));
            } else {
                Answer_Model answer_model = new Answer_Model();
                answer_model.setOrder(Integer.valueOf(stringOrder));
                answer_model.setAnswer(stringAnswer);
                answer_model.setPunctuation((int) dialogAddOrderAnswerBinding.sliderPunctuationOfOrderAnswer.getValue());
                answerOrderAdapter.answerModels.add(answer_model);
                answerOrderAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        Button buttonNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        buttonNeg.setOnClickListener(v12 -> dialog.dismiss());

    }

    // AÑADIR UNA RESPUESTA A UNA PREGUNTA DE OTRO TIPO DIFERENTE
    public void addAnswerOfOtherQuestion() {

        dialogAddOtherAnswerBinding = DialogAddOtherAnswerBinding.inflate(getLayoutInflater(), null, false);

        dialogAddOtherAnswerBinding.sliderPunctuationOfOtherAnswer.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                dialogAddOtherAnswerBinding.sliderPunctuationOfOtherAnswer.setValue((int) slider.getValue());
            }
        });

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.create_answer)
                .setPositiveButton(R.string.accept, null)
                .setNegativeButton(getString(R.string.cancel), null)
                .setView(dialogAddOtherAnswerBinding.getRoot())
                .show();

        Button buttonPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        buttonPos.setOnClickListener(v1 -> {
            String stringAnswer = dialogAddOtherAnswerBinding.editTextAnswer.getText().toString();
            if (stringAnswer.isEmpty()) {
                if (stringAnswer.isEmpty())
                    dialogAddOtherAnswerBinding.textInputLayoutAnswerText.setError(getString(R.string.required));
            } else {
                Answer_Model answer_model = new Answer_Model();
                answer_model.setAnswer(stringAnswer);
                answer_model.setPunctuation((int) dialogAddOtherAnswerBinding.sliderPunctuationOfOtherAnswer.getValue());
                if (dialogAddOtherAnswerBinding.radioButtonCorrect.isChecked())
                    answer_model.setCorrect(true);
                else
                    answer_model.setCorrect(false);
                answerOtherAdapter.answerModels.add(answer_model);
                answerOtherAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        Button buttonNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        buttonNeg.setOnClickListener(v12 -> dialog.dismiss());

    }

    // INICIAR ADAPTADOR DE LA LISTA DE RESPUESTAS EN DEPENDENCIA DEL TIPO DE PREGUNTA
    public void initAdapter(String questionType) {

        if (questionType.equalsIgnoreCase("Enlazar")) {
            answerLinkAdapter = new AnswerLink_Adapter(HomeNewQuestionFragment.questionModel.getAnswerModelList(), getActivity());
            recyclerAnswerList.setAdapter(answerLinkAdapter);
        } else if (questionType.equalsIgnoreCase("Ordenar")) {
            answerOrderAdapter = new AnswerOrder_Adapter(HomeNewQuestionFragment.questionModel.getAnswerModelList(), getActivity());
            recyclerAnswerList.setAdapter(answerOrderAdapter);
        } else {
            answerOtherAdapter = new AnswerOther_Adapter(HomeNewQuestionFragment.questionModel.getAnswerModelList(), getActivity());
            recyclerAnswerList.setAdapter(answerOtherAdapter);
        }
    }

    // CAMBIAR ADAPTADOR DEPENDENCIA DEL TIPO DE PREGUNTA
    public void changeAdapter(String questionType) {

        if (questionType.equalsIgnoreCase("Enlazar")) {
            HomeNewQuestionFragment.questionModel.getAnswerModelList().clear();
            answerLinkAdapter = new AnswerLink_Adapter(HomeNewQuestionFragment.questionModel.getAnswerModelList(), getActivity());
            recyclerAnswerList.setAdapter(answerLinkAdapter);
        } else if (questionType.equalsIgnoreCase("Ordenar")) {
            HomeNewQuestionFragment.questionModel.getAnswerModelList().clear();
            answerOrderAdapter = new AnswerOrder_Adapter(HomeNewQuestionFragment.questionModel.getAnswerModelList(), getActivity());
            recyclerAnswerList.setAdapter(answerOrderAdapter);
        } else {
            HomeNewQuestionFragment.questionModel.getAnswerModelList().clear();
            answerOtherAdapter = new AnswerOther_Adapter(HomeNewQuestionFragment.questionModel.getAnswerModelList(), getActivity());
            recyclerAnswerList.setAdapter(answerOtherAdapter);
        }
        this.questionType = questionType;
    }

    // AÑADIR LA LISTA DE RESPUESTAS A LA PREGUNTA
    public void addAnswerList(String questionType) {

        if (questionType.equalsIgnoreCase("Enlazar")) {
            HomeNewQuestionFragment.questionModel.setAnswerModelList(answerLinkAdapter.answerModels);
        } else if (questionType.equalsIgnoreCase("Ordenar")) {
            HomeNewQuestionFragment.questionModel.setAnswerModelList(answerOrderAdapter.answerModels);
        } else {
            HomeNewQuestionFragment.questionModel.setAnswerModelList(answerOtherAdapter.answerModels);
        }
    }

}