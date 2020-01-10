import 'dart:convert';

import 'package:memeory/model/attachment.dart';

class Meme {
  String id;
  String channelId;
  String caption;
  String publishedAt;
  List<Attachment> attachments;

  Meme({
    this.id,
    this.channelId,
    this.caption,
    this.publishedAt,
    this.attachments,
  });

  factory Meme.fromJson(Map<String, dynamic> json) {
    return Meme(
      id: json["id"],
      channelId: json["channelId"],
      caption: json["caption"],
      publishedAt: json["publishedAt"],
      attachments: List.of(json["attachments"])
          .map((i) => Attachment.fromJson(i))
          .toList(),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      "id": this.id,
      "channelId": this.channelId,
      "caption": this.caption,
      "publishedAt": this.publishedAt,
      "attachments": json.encode(this.attachments),
    };
  }
}
