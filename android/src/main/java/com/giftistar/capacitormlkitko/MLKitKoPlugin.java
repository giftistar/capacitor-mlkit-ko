package com.giftistar.capacitormlkitko;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "MLKitKo")
public class MLKitKoPlugin extends Plugin {

    private MLKitKo implementation;

    public MLKitKoPlugin() {
        implementation = new MLKitKo(getActivity());
    }

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");
        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void ocr(PluginCall call) {
        String image_uri = call.getString("value");
        implementation.ocr(image_uri, call);
    }

    @PluginMethod
    public void barcode(PluginCall call) {
        String image_uri = call.getString("value");
        implementation.barcode(image_uri, call);
    }
}
