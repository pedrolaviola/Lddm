package com.example.otto.trabalhopratico2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PdfFragment extends Fragment {
    TextView textView;
    String subjectName, fileType;
    Button pdf,link,video;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        subjectName = this.getArguments().getString("subjectName");
        //fileType = this.getArguments().getString("fileType");
        getActivity().setTitle(subjectName);
        return inflater.inflate(R.layout.fragment_pdf, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        pdf = (Button)view.findViewById(R.id.PdfButton);
        link = (Button)view.findViewById(R.id.LinkButton);
        video = (Button)view.findViewById(R.id.videoButton);
        textView = view.findViewById(R.id.InfoFrag);
        textView.setText("Videos, Links e PDF's de "+subjectName);

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mudarPg = new Intent(getActivity().getApplicationContext(),pdf_list.class);
                mudarPg.putExtra("nomeMateria",subjectName);
                startActivity(mudarPg);
            }
        });
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mudarPg = new Intent(getActivity().getApplicationContext(),link_list.class);
                mudarPg.putExtra("nomeMateria",subjectName);
                startActivity(mudarPg);
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mudarPg = new Intent(getActivity().getApplicationContext(),video_list.class);
                mudarPg.putExtra("nomeMateria",subjectName);
                startActivity(mudarPg);
            }
        });
    }
}
