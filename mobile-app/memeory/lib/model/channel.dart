import 'dart:convert';

class Channel {
  String id;
  String name;
  String provider;
  String uri;
  List<String> topics;

  Channel({
    this.id,
    this.name,
    this.provider,
    this.uri,
    this.topics,
  });

  factory Channel.fromJson(Map<String, dynamic> json) {
    return Channel(
      id: json["id"],
      name: json["name"],
      provider: json["provider"],
      uri: json["uri"],
      topics: List.of(json["topics"]).map((i) => json["topics"]).toList(),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      "id": this.id,
      "name": this.name,
      "provider": this.provider,
      "uri": this.uri,
      "topics": json.encode(this.topics),
    };
  }
}
