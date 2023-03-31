import Foundation
import Capacitor
import MLKitVision
import MLKitTextRecognitionKorean
import MLKitBarcodeScanning
import UIKit

@objc public class MLKitKo: NSObject {
@objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
    
    @objc public func ocr(_ call: CAPPluginCall) -> Void {
        
        var value = call.getString("value") ?? ""
        let koreanOptions = KoreanTextRecognizerOptions()
        let koreanTextRecognizer = TextRecognizer.textRecognizer(options:koreanOptions)

        print("ocr 1")
        if value.hasPrefix("data:image"){
            print("ocr 2")
            let base64ImageData = value.split(separator: ",").last!
            value = String(base64ImageData)
       
        }else{
            print("value:"+value)
        }
        print("ocr 3")
        
        guard let data = Data(base64Encoded: value), let image = UIImage(data: data) else {
            // Handle decoding error
            print("ocr 4")
            call.resolve([
                "value": ""
            ])
            return
        }
        let visionImage = VisionImage(image: image)
        visionImage.orientation = image.imageOrientation
       
        koreanTextRecognizer.process(visionImage) { result, error in
          guard error == nil, let result = result else {
            // Error handling
              print("ocr 5")
              call.resolve([
                  "value": ""
              ])
            return
          }
            let resultText = result.text
          // Recognized text
            print("ocr 6")
            call.resolve([
                "value": resultText
            ])
        }
    }
    
    @objc public func barcode(_ call: CAPPluginCall) -> Void {
        var value = call.getString("value") ?? ""
        print("barcode 1")
        if value.hasPrefix("data:image"){
            print("barcode 2")
            let base64ImageData = value.split(separator: ",").last!
            value = String(base64ImageData)
        }
        
        guard let data = Data(base64Encoded: value), let image = UIImage(data: data) else {
            // Handle decoding error
            print("barcode 3")
            
            call.resolve([
                "value": ""
            ])
            return
        }
        let visionImage = VisionImage(image: image)
        visionImage.orientation = image.imageOrientation
        print("barcode 4")
        
        let barcodeScanner = BarcodeScanner.barcodeScanner()
      
        barcodeScanner.process(visionImage) { barcodes, error in
          guard error == nil, let barcodes = barcodes, !barcodes.isEmpty else {
            // Error handling
              print("barcode 5")
              
              call.resolve([
                  "value": ""
              ])
            return
          }
            var barcodeValues = ""
          // Recognized barcodes
            print("barcode 6")
            
            for barcode in barcodes {
                print("barcode 7")
                
                let corners = barcode.cornerPoints
              let displayValue = barcode.displayValue
              let rawValue = barcode.rawValue
                barcodeValues += rawValue! + ","
            }
            print("barcode 8")
            
            call.resolve([
                "value": barcodeValues
            ])
        }
        

    }
}
