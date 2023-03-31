package com.giftistar.capacitormlkitko;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
        Log.i("ocr", "ocr image_uri:" + image_uri);
        try {
            if (recognizer == null) {
                recognizer = TextRecognition.getClient(new KoreanTextRecognizerOptions.Builder().build());
            }
            String imageString = image_uri;
            if (imageString.startsWith("data:")) {
                imageString = image_uri.substring(imageString.indexOf(",") + 1);
            }
            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);

            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            InputImage image = InputImage.fromBitmap(bitmap, 0);

            recognizer
                .process(image)
                .addOnCompleteListener(
                    new OnCompleteListener<Text>() {
                        @Override
                        public void onComplete(@NonNull Task<Text> task) {
                            JSObject ret = new JSObject();
                            Log.i("ocr", "onComplete task.getResult():" + task.getResult());
                            if (task.getResult() != null && task.getResult().getText() != null) {
                                Log.i("ocr", "onComplete task.getResult() text:" + task.getResult().getText());
                                ret.put("value", task.getResult().getText());
                            } else {
                                ret.put("value", "");
                            }
                            call.resolve(ret);
                        }
                    }
                )
                .addOnFailureListener(
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("ocr", "onFailure task.getResult() text:" + e.getMessage());
                            JSObject ret = new JSObject();
                            ret.put("value", "");
                            call.resolve(ret);
                        }
                    }
                );
        } catch (Exception e) {
            Log.i("ocr", "ocr error:" + e.getMessage());
            JSObject ret = new JSObject();
            ret.put("value", "");
            call.resolve(ret);

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

            String imageString = image_uri;
            if (imageString.startsWith("data:")) {
                imageString = image_uri.substring(imageString.indexOf(",") + 1);
            }
            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);

            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            InputImage image = InputImage.fromBitmap(bitmap, 0);

            barcodeScanner
                .process(image)
                .addOnCompleteListener(
                    new OnCompleteListener<List<Barcode>>() {
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
                    }
                );
        } catch (Exception e) {
            JSObject ret = new JSObject();
            ret.put("value", "");
            call.resolve(ret);

            return null;
        }

        return image_uri;
    }
}
