#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(CapacitorMLKitPlugin, "CapacitorMLKit",
           CAP_PLUGIN_METHOD(echo, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(ocr, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(barcode, CAPPluginReturnPromise);
)
