package com.giftistar.capacitormlkitko;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;

import java.util.List;

public class MLKitKo {

    public static TextRecognizer recognizer;
    public static BarcodeScanner barcodeScanner;

    Activity activity;

    public MLKitKo(Activity activity) {
        this.activity = activity;
    }

    public String echo(String value) {
        Log.i("Echo", value);

        return value;
    }

    public String ocr(String image_uri, PluginCall call) {
        Log.i("image_uri", "ocr image_uri:" + image_uri);
        try {
            if (recognizer == null) {
                recognizer = TextRecognition.getClient(new KoreanTextRecognizerOptions.Builder().build());
            }

            InputImage image =
                    InputImage.fromFilePath(activity, Uri.parse(image_uri));
            recognizer.process(image).addOnCompleteListener(new OnCompleteListener<Text>() {
                @Override
                public void onComplete(@NonNull Task<Text> task) {
                    JSObject ret = new JSObject();
                    if (task.getResult() != null && task.getResult().getText() != null) {
                        ret.put("value", task.getResult().getText());
                    } else {
                        ret.put("value", "");
                    }
                    call.resolve(ret);
                }
            });

        } catch (Exception e) {
            return null;
        }

        return null;
    }

    public String barcode(String image_uri, PluginCall call) {
        Log.i("image_uri", "barcode image_uri:" + image_uri);

        try {
            if (barcodeScanner == null) {
                barcodeScanner = BarcodeScanning.getClient();
            }

            InputImage image =
                    InputImage.fromFilePath(activity, Uri.parse(image_uri));

            barcodeScanner.process(image).addOnCompleteListener(new OnCompleteListener<List<Barcode>>() {
                @Override
                public void onComplete(@NonNull Task<List<Barcode>> task) {
                    JSObject ret = new JSObject();
                    String result = "";
                    if (task.getResult() != null && task.getResult().size() > 0) {
                        for (int i = 0; i < task.getResult().size(); i++) {
                            if (i > 0) {
                                result += ",";
                            }
                            result += task.getResult().get(i).getRawValue();
                        }
                    }
                    ret.put("value", result);
                    call.resolve(ret);

                }
            });

        } catch (Exception e) {
            return null;
        }

        return image_uri;
    }
}
