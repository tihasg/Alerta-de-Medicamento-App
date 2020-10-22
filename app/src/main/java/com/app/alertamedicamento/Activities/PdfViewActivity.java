package com.app.alertamedicamento.Activities;

import android.content.res.AssetFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.alertamedicamento.R;
import com.app.alertamedicamento.business.Dispositivo;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

public class PdfViewActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {

    private static final String TAG = PdfViewActivity.class.getSimpleName();
    private PDFView pdfView = null;
    private Integer pageNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        initComponents();
    }

    public void voltar(View view) {
        finish();
    }

    private void initComponents() {
        pdfView = (PDFView) findViewById(R.id.pdfView);

//        try {
//
//            AssetFileDescriptor file = null;
//            if (Dispositivo.PdfFileName.equals("contato.pdf")) {
//                file = getResources().openRawResourceFd(R.raw.contato);
//                ((TextView) findViewById(R.id.tvTitle)).setText("Contato");
//            } else
//            if (Dispositivo.PdfFileName.equals("manual.pdf")) {
//                file = getResources().openRawResourceFd(R.raw.manual);
//                ((TextView) findViewById(R.id.tvTitle)).setText("Manual");
//            }
//
//            pdfView.fromStream(file.createInputStream())
//                    .defaultPage(pageNumber)
//                    .onPageChange(this)
//                    .enableAnnotationRendering(true)
//                    .onLoad(this)
//                    .scrollHandle(new DefaultScrollHandle(this))
//                    .load();
//
//        } catch (Exception e) {
//            Log.e(TAG, e.getMessage());
//        }
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        Log.e(TAG, "title = " + meta.getTitle());
        Log.e(TAG, "author = " + meta.getAuthor());
        Log.e(TAG, "subject = " + meta.getSubject());
        Log.e(TAG, "keywords = " + meta.getKeywords());
        Log.e(TAG, "creator = " + meta.getCreator());
        Log.e(TAG, "producer = " + meta.getProducer());
        Log.e(TAG, "creationDate = " + meta.getCreationDate());
        Log.e(TAG, "modDate = " + meta.getModDate());

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
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", Dispositivo.PdfFileName, page + 1, pageCount));
    }

}
