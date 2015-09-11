#include <OneWire.h>
//#include <SoftwareSerial.h>
// OneWire DS18S20, DS18B20, DS1822 Temperature Example
//
// http://www.pjrc.com/teensy/td_libs_OneWire.html
//
// The DallasTemperature library can do all this work for you!
// http://milesburton.com/Dallas_Temperature_Control_Library

//Gestione stringa in input
String inputString = "";         // a string to hold incoming data
boolean stringComplete = false;  // whether the string is complete

//Temperatura
OneWire  ds(10);  // on pin 10 (a 4.7K resistor is necessary)

//Luce
int photocell1Pin = 0;     // the first cell and 10K pulldown are connected to a0
int photocell2Pin = 1;     // the second cell and 10K pulldown are connected to a1
int photocell1Reading;     // the first analog reading from the sensor divider
int photocell2Reading;     // the second analog reading from the sensor divider

//unsigned long time;
//int led = 13;
//SoftwareSerial mySerial(2, 3); // RX, TX

void setup(void) {
  //pinMode(led,OUTPUT);
  //digitalWrite(led, HIGH); 
  Serial.begin(9600);
    //mySerial.begin(9600);
  // reserve 100 bytes for the inputString:
  inputString.reserve(100);
}

void loop(void) {
//digitalWrite(led, HIGH); 
  if (stringComplete) {
    
    if (inputString.equals("therm1\n")) {
      float temp=getTemperature();
      String tempString = String(temp);
      tempString+='\n';
      Serial.print(tempString);
      //digitalWrite(led, LOW); 
      //delay(500);
      //mySerial.print(tempString);
      //Serial.println(temp, 4);
          // clear the string:
    }
    else if(inputString.equals("lm1\n")) { 
      analogRead(photocell1Pin); //Pulisce la lettura
  photocell1Reading = analogRead(photocell1Pin);
  Serial.println(photocell1Reading);     // the raw analog reading  
    }
    else
    Serial.println("errore\n");
    
    inputString = "";
    stringComplete = false;
  }
    else
    serialEvent(); //call the function
}

/*
  SerialEvent occurs whenever a new data comes in the
 hardware serial RX.  This routine is run between each
 time loop() runs, so using delay inside loop can delay
 response.  Multiple bytes of data may be available.
 */
void serialEvent() {
  while (Serial.available()) {
    // get the new byte:
    char inChar = (char)Serial.read();
    //mySerial.print(inChar);
    // add it to the inputString:
    inputString += inChar;
    // if the incoming character is a newline, set a flag
    // so the main loop can do something about it:
    if (inChar == '\n') {
      stringComplete = true;
    }
  }
}

float getTemperature() {
  byte i;
  byte present = 0;
  byte data[12];
  byte addr[8];
  float celsius;

//28 FF C5 1F 12 14 0 C8
addr[0]=0x28;
addr[1]=0xFF;
addr[2]=0xC5;
addr[3]=0x1F;
addr[4]=0x12;
addr[5]=0x14;
addr[6]=0x0;
addr[7]=0xC8;

  ds.reset();
  ds.select(addr);
  ds.write(0x44, 1);        // start conversion, with parasite power on at the end
  
  delay(1000);     // maybe 750ms is enough, maybe not
  // we might do a ds.depower() here, but the reset will take care of it.
  
  present = ds.reset();
  ds.select(addr);    
  ds.write(0xBE);         // Read Scratchpad

  for ( i = 0; i < 9; i++) {           // we need 9 bytes
    data[i] = ds.read();
  }

  // Convert the data to actual temperature
  // because the result is a 16 bit signed integer, it should
  // be stored to an "int16_t" type, which is always 16 bits
  // even when compiled on a 32 bit processor.
  int16_t raw = (data[1] << 8) | data[0];

    byte cfg = (data[4] & 0x60);
    // at lower res, the low bits are undefined, so let's zero them
    if (cfg == 0x00) raw = raw & ~7;  // 9 bit resolution, 93.75 ms
    else if (cfg == 0x20) raw = raw & ~3; // 10 bit res, 187.5 ms
    else if (cfg == 0x40) raw = raw & ~1; // 11 bit res, 375 ms
    //// default is 12 bit resolution, 750 ms conversion time
 // }
  celsius = (float)raw / 16.0;
  return celsius;
}
