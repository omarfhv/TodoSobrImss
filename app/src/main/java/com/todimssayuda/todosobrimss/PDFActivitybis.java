package com.todimssayuda.todosobrimss;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.util.List;

public class PDFActivitybis extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, View.OnClickListener {
    public static final String SAMPLE_FILE = "android_tutorial.pdf";
    PDFView pdfView;
    String pdfFileName;
    String TAG = "PdfActivity";
    int position = -1;
    Button boton ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setContentView(R.layout.activity_pdf);

        AdView mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        boton = findViewById(R.id.boton);
        boton.setOnClickListener(this);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        position = getIntent().getIntExtra("position", -1);

        pdfFileName = PDFViewer.fileList.get(position).getName();

        pdfView.fromFile(PDFViewer.fileList.get(position))
                .enableSwipe(true)
                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }



    @Override
    public void onPageChanged(int page, int pageCount) {

        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));

    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), PDFViewer.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), PDFViewer.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        File outputFile = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + PDFViewer.fileList.get(position).getName());
        String s = PDFViewer.fileList.get(position).getName();

        File pdf;

        if (outputFile.getName().endsWith(".aspx")) {
            s = s.substring(0, s.length() - 4);
            pdf = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + s + "pdf");
            outputFile.renameTo(pdf);

        } else {
            pdf = outputFile;
        }

        Uri imageUri = FileProvider.getUriForFile(PDFActivitybis.this, "com.todimssayuda.todosobrimss.provider", pdf);
        System.out.println(imageUri);
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setType("application/pdf");
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        //share.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(share, "Share"));
        //startActivity(share);

    }
}
