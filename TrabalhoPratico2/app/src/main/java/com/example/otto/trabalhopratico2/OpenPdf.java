package com.example.otto.trabalhopratico2;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class OpenPdf extends AppCompatActivity {

    String caminho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_pdf);

        Bundle getInfo = getIntent().getExtras();
        caminho = getInfo.getString("caminho");

        PDFView pdfView = (PDFView)findViewById(R.id.pdfView);
        pdfView.fromUri(Uri.parse(caminho)).load();
    }
}
