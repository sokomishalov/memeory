class Topic {
  String id;
  String caption;

  Topic({
    this.id,
    this.caption,
  });

  factory Topic.fromJson(Map<String, dynamic> json) {
    return Topic(
      id: json["id"],
      caption: json["caption"],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      "id": this.id,
      "caption": this.caption,
    };
  }
}
