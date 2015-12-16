#include <OneWire.h>
#include <DallasTemperature.h>
#include <Servo.h>

#include "U8glib.h"

//Temperatura
#define SERVO_WINDOW_PIN 9
//ONEWIRE----------------------------------------------------------------------START
// Data wire is plugged into port 8 on the Arduino
#define ONE_WIRE_BUS 8
OneWire oneWire(ONE_WIRE_BUS);
// Pass our oneWire reference to Dallas Temperature.
DallasTemperature sensors(&oneWire);
DeviceAddress tempDeviceAddress;
int  resolution = 10;
unsigned long lastTempRequest = 0;
int  delayInMillis = 0;
float temperature = 0.0;
//ONEWIRE----------------------------------------------------------------------STOP
Servo window;
bool windowOpen;
int posServoWindowOpened = 170;
int posServoWindowClosed = 88;
int posServoWindow = posServoWindowClosed;    // variable to store the servo position
unsigned long lastWindowServoCommand = 0;
int fanPin = 2;
bool fanOn;
int boilerPin = 5;
bool boilerOn;

//Luce
int photocell1Pin = 0;     // the first cell and 10K pulldown are connected to a0
int laserphotocell1Pin = 1;     // the second cell and 10K pulldown are connected to a1 (laser)
int photocell1Reading;     // the first analog reading from the sensor divider
int laserphotocell1Reading;     // the second analog reading from the sensor divider
bool lightOn;
int lightPin = 12;
Servo shutter;
int shutterPin = 6;
int posServoShutterOpened = 60;
int posServoShutterClosed = 160;
int posServoShutter = posServoShutterClosed;    // variable to store the servo position

int shutterOpen;
unsigned long lastShutterServoCommand = 0;

//SoftwareSerial mySerial(2, 3); // RX, TX

//Sicurezza
const int pirPin = 4;    //the digital pin connected to the PIR sensor's output
unsigned long pirStartCalibrationTime;
const int pirCalibrationTime = 30; //come da datasheet (up to 60 secs)
boolean pirCalibrated; //se true si può leggere il valore restituito dal sensore
const int flameSensorPin = 2; //the analog pin connected to the flame sensor's output

//Gestione stringa in input
String inputString = "";         // a string to hold incoming data
boolean stringComplete = false;  // whether the string is complete

//Garage
Servo garage;
int garagePin = 10;
int posServoGarageOpened = 110;
int posServoGarageClosed = 20;
int posServoGarage = posServoGarageClosed;    // variable to store the servo position

int garageOpen;
unsigned long lastGarageServoCommand = 0;

U8GLIB_SSD1306_128X64 u8g(U8G_I2C_OPT_NO_ACK);  // Display which does not send AC

#define thermometer_width 18
#define thermometer_height 60
static unsigned char thermometer[] U8G_PROGMEM= {
   0x80, 0x07, 0x00, 0xc0, 0x0e, 0x00, 0x60, 0x18, 0x00, 0x20, 0x10, 0x00,
   0x30, 0x30, 0x00, 0x20, 0x10, 0x00, 0x30, 0x30, 0x00, 0x20, 0x10, 0x00,
   0x30, 0x30, 0x00, 0x20, 0x10, 0x00, 0x30, 0x30, 0x00, 0x20, 0x10, 0x00,
   0x30, 0xb0, 0x00, 0x20, 0xf0, 0x00, 0x30, 0x10, 0x00, 0x20, 0x10, 0x00,
   0x30, 0x30, 0x00, 0x20, 0x10, 0x00, 0x30, 0x30, 0x00, 0x20, 0x10, 0x00,
   0x30, 0x30, 0x00, 0x20, 0xf0, 0x01, 0x30, 0x10, 0x00, 0x20, 0x10, 0x00,
   0x30, 0x30, 0x00, 0x20, 0x10, 0x00, 0x30, 0x30, 0x00, 0xa0, 0x17, 0x00,
   0xb0, 0x17, 0x00, 0xa0, 0xf7, 0x01, 0xb0, 0x37, 0x00, 0xa0, 0x17, 0x00,
   0xb0, 0x37, 0x00, 0x20, 0x17, 0x00, 0xb0, 0x37, 0x00, 0xa0, 0x17, 0x00,
   0xb0, 0x37, 0x00, 0x20, 0xb7, 0x00, 0xb0, 0xf7, 0x00, 0xa0, 0x17, 0x00,
   0xb0, 0x17, 0x00, 0x20, 0x37, 0x00, 0xb0, 0x17, 0x00, 0xb0, 0x37, 0x00,
   0xb8, 0x77, 0x00, 0x8c, 0xcf, 0x00, 0xe6, 0x9f, 0x01, 0xf6, 0xbf, 0x01,
   0xfa, 0x7f, 0x03, 0xfb, 0x7f, 0x03, 0xfb, 0x7f, 0x03, 0xfb, 0x7f, 0x03,
   0xfb, 0x7f, 0x03, 0xfa, 0x7f, 0x03, 0xf2, 0x3f, 0x01, 0xe6, 0xbf, 0x01,
   0xcc, 0xcf, 0x00, 0x18, 0x60, 0x00, 0xf0, 0x3f, 0x00, 0x80, 0x0f, 0x00 };
void setup(void) {

  pirCalibrated = false;
  pirStartCalibrationTime = millis();
  pinMode(pirPin, INPUT);
  //digitalWrite(pirPin, LOW);

  pinMode(lightPin, OUTPUT);
  digitalWrite(lightPin, LOW);
  lightOn = false;
  
  shutter.attach(shutterPin);
  shutter.write(posServoShutter);
  lastShutterServoCommand = millis();
  shutterOpen = false;

  pinMode(boilerPin, OUTPUT);
  digitalWrite(boilerPin, LOW);
  boilerOn = false;

  pinMode(fanPin, OUTPUT);
  digitalWrite(fanPin, LOW);
  fanOn = false;

  window.attach(SERVO_WINDOW_PIN);
  window.write(posServoWindow);
  lastWindowServoCommand = millis();
  windowOpen = false;

  garage.attach(garagePin);
  garage.write(posServoGarage);
  lastGarageServoCommand = millis();
  garageOpen = false;

  Serial.begin(115200);
  //mySerial.begin(9600);
  // reserve 100 bytes for the inputString:
  inputString.reserve(100);
  initThermometer();

}

void draw(char * toPrint) {
  // graphic commands to redraw the complete screen should be placed here  
  //u8g.setFont(u8g_font_unifont);
  u8g.setFont(u8g_font_osb21);
  u8g.drawStr( 22, 45, toPrint);
  u8g.drawXBMP( 0, 2, 18, 60, thermometer);

}

void loop(void) {
  String tempString = String(temperature);
  tempString += "\260C";
  char tempCharArray[10];
  tempString.toCharArray(tempCharArray,10);
  // picture loop
  u8g.firstPage();  
  do {
    draw(tempCharArray);
  } while( u8g.nextPage() );
  
  updateTemperature(); // ad ogni ciclo controlla se è pronto il termometro e nel caso aggiorna il valore della temperatura.

  if(millis() - lastShutterServoCommand > 500) { // se passano 500ms disattiva il servo per evitare il "buzz"
    shutter.detach();
  }

  if(millis() - lastWindowServoCommand > 500) { // se passano 500ms disattiva il servo per evitare il "buzz"
    window.detach();
  }

    if(millis() - lastGarageServoCommand > 100) { // se passano 100ms aggiorna il valore della posizione della porta del garage
    setGarage();
    lastGarageServoCommand = millis();
  }
  

  if (stringComplete) {

    if (inputString.equals("therm1\n")) {
      Serial.println(temperature, resolution - 8); //Temperatura con le giuste cifre decimali
    }
    else if (inputString.equals("lm1\n")) {
      analogRead(photocell1Pin); //Pulisce la lettura
      photocell1Reading = analogRead(photocell1Pin);
      Serial.println(photocell1Reading);     // valore raw.
    }
    else if (inputString.equals("laser1\n")) {
      analogRead(laserphotocell1Pin); //Pulisce la lettura
      laserphotocell1Reading = analogRead(laserphotocell1Pin);
      Serial.println(laserphotocell1Reading);     // valore raw.
    }
    else if (inputString.equals("pir1\n")) {
      if (!pirCalibrated) {
        if ((millis() - pirStartCalibrationTime) / 1000 >= pirCalibrationTime) {
          pirCalibrated = true;
          Serial.println((digitalRead(pirPin) == HIGH) ? "true" : "false"); //se è appena scaduto il tempo per la calibrazione (eseguito solo una volta)
        }
        else {
          Serial.println("false"); //solo nella fase di calibrazione
        }
      }
      else {
        Serial.println((digitalRead(pirPin) == HIGH) ? "true" : "false"); // il sensore mantiene il valore HIGH per circa 5sec se rileva un movimento
      }
    }
    else if (inputString.equals("flame1\n")) {
      bool fire = checkFlameSensor();
      Serial.println(fire ? "true" : "false");
    }
    else if (inputString.equals("fan1\n")) {
      setFan();
      Serial.println(fanOn ? "true" : "false");
    }
    else if (inputString.equals("boiler1\n")) {
      setBoiler();
      Serial.println(boilerOn ? "true" : "false");
    }
    else if (inputString.equals("light1\n")) {
      setLight();
      Serial.println(lightOn ? "true" : "false");
    }
    else if (inputString.equals("shutter1\n")) {
      shutter.attach(shutterPin);
      setShutter();
      lastShutterServoCommand = millis();
      Serial.println(shutterOpen ? "true" : "false");
    }
    else if (inputString.equals("window1\n")) {
      window.attach(SERVO_WINDOW_PIN);
      setWindow();
      lastWindowServoCommand = millis();
      Serial.println(windowOpen ? "true" : "false");
    }
    else if (inputString.equals("garage1\n")) {
      garage.attach(garagePin);
      garageOpen=garageOpen ? false : true;
      Serial.println(garageOpen ? "true" : "false");
    }
    else
      Serial.println("errore");
    // clear the string:
    inputString = "";
    stringComplete = false;
  }
  else
    serialEvent(); // solo se la stringa non è completa
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

bool checkFlameSensor() {
  analogRead(flameSensorPin); //pulizia lettura
  int sensorReading = analogRead(flameSensorPin);
  // map the sensor range:
  // ex: 'long int map(long int, long int, long int, long int, long int)'
  // 0: A fire closer than 1.5 feet away.
  // 1: A fire between 1-3 feet away.
  // 2: No fire detected.
  int range = map(sensorReading, 0, 1024, 0, 3);
  return((range < 2) ? true : false);
}

void setBoiler () {
  if (boilerOn == false) { //se è spento l'accende
    digitalWrite(boilerPin, HIGH);
    boilerOn = true;
  }
  else {// se è acceso lo spegne
    digitalWrite(boilerPin, LOW);
    boilerOn = false;
  }
}

void setFan () {
  if (fanOn == false) { //se è spenta l'accende
    digitalWrite(fanPin, HIGH);
    fanOn = true;
  }
  else {// se è accesa la spegne
    digitalWrite(fanPin, LOW);
    fanOn = false;
  }
}

void setLight () {
  if (lightOn == false) { //se è spenta l'accende
    digitalWrite(lightPin, HIGH);
    lightOn = true;
  }
  else {// se è accesa la spegne
    digitalWrite(lightPin, LOW);
    lightOn = false;
  }
}

void setShutter () {
  if (shutterOpen == false) { //se è chiusa la apre
    posServoShutter = posServoShutterOpened;
    shutterOpen = true;
  }
  else {// se è aperta la chiude
    posServoShutter = posServoShutterClosed;
    shutterOpen = false;
  }
  shutter.write(posServoShutter);
}

void setWindow () {
  if (windowOpen == false) { //se è chiusa la apre
    posServoWindow = posServoWindowOpened;
    windowOpen = true;
  }
  else {// se è aperta la chiude
    posServoWindow = posServoWindowClosed;
    windowOpen = false;
  }
  window.write(posServoWindow);
}

void setGarage () {
  if (garageOpen == true) { //se è chiusa e la si imposta aperta la apre
    if(posServoGarage < posServoGarageOpened)
      posServoGarage += 1;
      else
      garage.detach();
  }
  else {// se è aperta la si imposta chiusa la chiude
    if(posServoGarage > posServoGarageClosed)
      posServoGarage -= 1;
      else
      garage.detach();
  }
  garage.write(posServoGarage);
}

void initThermometer() {
  sensors.begin();
  sensors.getAddress(tempDeviceAddress, 0);
  sensors.setResolution(tempDeviceAddress, resolution);

  sensors.setWaitForConversion(false);
  sensors.requestTemperatures();
  delayInMillis = 750 / (1 << (12 - resolution));
  lastTempRequest = millis();
}

void updateTemperature() {
  if (millis() - lastTempRequest >= delayInMillis) // waited long enough??
  {
    temperature = sensors.getTempCByIndex(0);

    sensors.requestTemperatures();
    delayInMillis = 750 / (1 << (12 - resolution));
    lastTempRequest = millis();
  }
}


