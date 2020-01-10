import 'package:flutter/foundation.dart';

enum ScrollingAxis {
  VERTICAL,
  HORIZONTAL,
}

ScrollingAxis scrollingAxisFromString(String str) {
  return ScrollingAxis.values.firstWhere(
    (e) => describeEnum(e) == str,
    orElse: () => ScrollingAxis.VERTICAL,
  );
}
