package cu.cujae.gilsoft.tykeprof.app.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.data.entity.Punctuation;
import cu.cujae.gilsoft.tykeprof.databinding.RankingChartFragmentBinding;


public class RankingChartFragment extends Fragment {

    private RankingChartFragmentBinding binding;
    private BarChart rankingChart;

    public RankingChartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = RankingChartFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rankingChart = binding.rankingChart;
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RankingFragment.rankingViewModel.getAllPunctuations().observe(getParentFragment().getViewLifecycleOwner(), punctuationList -> {
            initChart((ArrayList<Punctuation>) punctuationList);
            rankingChart.setVisibleXRangeMaximum(5);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void initChart(ArrayList<Punctuation> punctuations) {

        ArrayList<BarEntry> arrayList = new ArrayList<>();
        ArrayList<String> strings = new ArrayList<>();
        int i = 0;

        for (Punctuation punctuation : punctuations) {
            arrayList.add(new BarEntry(i, punctuation.getPunctuation()));
            strings.add(punctuation.getPlayer().getUserName());
            i++;
        }

        XAxis xAxis = rankingChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(strings));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setTextSize(13f);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        YAxis yAxisLeft = rankingChart.getAxisLeft();
        yAxisLeft.setTextColor(Color.GRAY);

        YAxis yAxisRigth = rankingChart.getAxisRight();
        yAxisRigth.setTextColor(Color.GRAY);

        BarDataSet barDataSet = new BarDataSet(arrayList, getString(R.string.players));
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.GRAY);
        barDataSet.setValueTextSize(15f);

        BarData barData = new BarData(barDataSet);
        rankingChart.getLegend().setTextColor(Color.GRAY);
        rankingChart.animateY(1000);
        rankingChart.setVisibleXRangeMaximum(5);
        rankingChart.getDescription().setText(getString(R.string.punctuation));
        rankingChart.getDescription().setTextColor(Color.GRAY);
        rankingChart.getDescription().setTextSize(15f);
        rankingChart.getDescription().setEnabled(true);
        rankingChart.setData(barData);
    }

}