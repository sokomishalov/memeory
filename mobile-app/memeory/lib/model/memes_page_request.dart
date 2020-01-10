import 'package:memeory/util/consts/consts.dart';

class MemesPageRequest {
  String topicId;
  String providerId;
  String channelId;
  int pageNumber;
  int pageSize;

  MemesPageRequest({
    this.topicId,
    this.providerId,
    this.channelId,
    this.pageNumber = 0,
    this.pageSize = MEMES_COUNT_ON_THE_PAGE,
  });

  factory MemesPageRequest.fromJson(Map<String, dynamic> json) {
    return MemesPageRequest(
      topicId: json["topicId"],
      providerId: json["providerId"],
      channelId: json["channelId"],
      pageNumber: int.parse(json["pageNumber"]),
      pageSize: int.parse(json["pageSize"]),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      "topicId": this.topicId,
      "providerId": this.providerId,
      "channelId": this.channelId,
      "pageNumber": this.pageNumber,
      "pageSize": this.pageSize,
    };
  }
}
