import 'dart:convert';

import 'package:memeory/model/attachment.dart';

class Meme {
  String id;
  String channelId;
  String channelName;
  String caption;
  String publishedAt;
  List<Attachment> attachments;

  Meme({
    this.id,
    this.channelId,
    this.channelName,
    this.caption,
    this.publishedAt,
    this.attachments,
  });

  factory Meme.fromJson(Map<String, dynamic> json) {
    return Meme(
      id: json["id"],
      channelId: json["channelId"],
      channelName: json["channelName"],
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
      "channelName": this.channelName,
      "caption": this.caption,
      "publishedAt": this.publishedAt,
      "attachments": json.encode(this.attachments),
    };
  }
}
