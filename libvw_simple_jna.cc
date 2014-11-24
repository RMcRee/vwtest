#include <stdio.h>
#include "../vowpal_wabbit/vowpalwabbit/parser.h"
#include "../vowpal_wabbit/vowpalwabbit/vw.h"
#include "../vowpal_wabbit/vowpalwabbit/multiclass.h"

using namespace std;

vw* vw; // array of vw instances, caller keeps them all straight by specifying an index

extern "C" void initialize(int index, const char* command) {
  vw = VW::initialize(command);
  //cerr << "Java initialize called on " << vw << endl;
}

extern "C" double getPrediction(int index, const char* example_string) {
  //cerr << "Example string is " << example_string << endl;
  example *vec2 = VW::read_example(*vw, (char*)example_string);
  vw->learn(vec2);
  float prediction;
  if (vw->p->lp.parse_label == MULTICLASS::mc_label.parse_label) {
            prediction = ((MULTICLASS::multiclass*) vec2->ld)->prediction;
  }
  else {
            prediction = ((label_data*) vec2->ld)->prediction;
  }

  VW::finish_example(*vw, vec2);
  //cerr << "example prediction = " << prediction << endl;
  return prediction;
}

extern "C" void closeInstance(int index) {
  VW::finish(*vw);
}
