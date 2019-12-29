import 'package:flutter/foundation.dart';

enum AttachmentType {
  IMAGE,
  VIDEO,
  NONE,
}

AttachmentType attachmentTypeFromString(String str) {
  return AttachmentType.values.firstWhere(
    (e) => describeEnum(e) == str,
    orElse: () => AttachmentType.NONE,
  );
}
