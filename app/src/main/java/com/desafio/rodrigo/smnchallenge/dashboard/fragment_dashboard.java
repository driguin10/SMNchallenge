package com.desafio.rodrigo.smnchallenge.dashboard;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.desafio.rodrigo.smnchallenge.R;
import com.desafio.rodrigo.smnchallenge.loja.classes.Atividade;
import com.desafio.rodrigo.smnchallenge.loja.classes.Loja;
import java.util.ArrayList;
import java.util.List;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;


public class fragment_dashboard extends Fragment {

    private PieChartView chart;
    private PieChartData data;
    //configuracoes do chart
    private boolean hasLabels = false;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = false;
    private boolean hasLabelForSelected = false;
    //-------------------------
    TextView labelResumo,labelLegendaEntrada,labelLegendaSaida;
    Double entradas = 0.0;
    Double saidas = 0.0;
    Double lucro_prejuizo = 0.0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_dashboard, container, false);
        Bundle bundle = getActivity().getIntent().getExtras();
        labelResumo = (TextView) v.findViewById(R.id.valor_resumo);
        labelLegendaEntrada = (TextView) v.findViewById(R.id.legendaEntradas);
        labelLegendaSaida = (TextView) v.findViewById(R.id.legendaSaidas);
        Loja classAtividade = (Loja) bundle.getSerializable("classe");
        if(classAtividade.getAtividades()!=null) {
            List<Atividade> atividades = classAtividade.getAtividades();
            for(int i=0;i<atividades.size();i++){
                entradas = entradas+ atividades.get(i).getEntrada();
                saidas = saidas+ atividades.get(i).getSaida();
            }
            if(entradas > saidas) {
                lucro_prejuizo = entradas - saidas;
                labelResumo.setText("Lucro de : R$"+lucro_prejuizo);
            }else
            if(entradas < saidas) {
                lucro_prejuizo =  saidas - entradas;
                labelResumo.setText("Prejuizo de : R$"+lucro_prejuizo);
            }

            labelLegendaEntrada.setText("Entradas: R$"+String.valueOf(entradas));
            labelLegendaSaida.setText("Saidas: R$"+String.valueOf(saidas));

        }
        chart = (PieChartView) v.findViewById(R.id.grafico);
        chart.setOnValueTouchListener(new ValueTouchListener());

        generateData();
        return  v;
    }

    private void generateData() {
        List<SliceValue> values = new ArrayList<SliceValue>();

        Float valorEntrada = entradas.floatValue();
        Float valorSaida = saidas.floatValue();
        SliceValue sliceValueEntrada = new SliceValue(valorEntrada, ChartUtils.COLOR_GREEN);
        SliceValue sliceValueSaida = new SliceValue(valorSaida, ChartUtils.COLOR_RED);
        values.add(sliceValueEntrada);
        values.add(sliceValueSaida);

        data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);
        chart.setPieChartData(data);
    }

    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
        }

        @Override
        public void onValueDeselected() {
        }

    }
}
