import 'package:flutter/foundation.dart';
import 'package:memeory/model/attachment_type.dart';

class Attachment {
  String url;
  AttachmentType type;
  double aspectRatio;

  Attachment({
    this.url,
    this.type,
    this.aspectRatio,
  });

  factory Attachment.fromJson(Map<String, dynamic> json) {
    return Attachment(
      url: json["url"],
      type: attachmentTypeFromString(json["type"]),
      aspectRatio: json["aspectRatio"] ?? 1,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      "url": this.url,
      "type": describeEnum(this.type),
      "aspectRatio": this.aspectRatio,
    };
  }
}
