import 'package:flutter/foundation.dart';

enum MemesOrientation {
  VERTICAL,
  HORIZONTAL,
}

MemesOrientation orientationFromString(String str) {
  return MemesOrientation.values.firstWhere(
    (e) => describeEnum(e) == str,
    orElse: () => MemesOrientation.VERTICAL,
  );
}
