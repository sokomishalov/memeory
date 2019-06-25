import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/cupertino.dart';
import 'package:memeory/api/channels.dart';
import 'package:memeory/common/message/messages.dart';
import 'package:memeory/util/time.dart';

import 'attachments/photo.dart';
import 'attachments/video.dart';

mixin MemesMixin {
  Widget prepareHeader(item, context) {
    return Container(
      padding: EdgeInsets.only(
        left: 10,
        top: 10,
        right: 15,
        bottom: 10,
      ),
      child: Row(
        children: <Widget>[
          Container(
            width: 35,
            height: 35,
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(10),
              image: DecorationImage(
                image: CachedNetworkImageProvider(
                  getLogoUrl(item["channelId"]),
                ),
              ),
            ),
          ),
          Spacer(flex: 1),
          Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              Text(
                item["channelName"],
                style: TextStyle(fontSize: 12),
              ),
              Container(
                padding: EdgeInsets.only(top: 4),
                child: Opacity(
                  child: Text(
                    timeAgo(item["publishedAt"]),
                    style: TextStyle(fontSize: 12),
                  ),
                  opacity: 0.5,
                ),
              ),
            ],
          ),
          Spacer(flex: 20),
          GestureDetector(
            onTap: () => onTapEllipsis(context),
            child: Icon(CupertinoIcons.ellipsis),
          ),
        ],
      ),
    );
  }

  Widget prepareCaption(item, context) {
    return Container(
      padding: EdgeInsets.only(left: 10, right: 10, bottom: 10),
      child: Text(
        item["caption"],
        style: TextStyle(fontSize: 16),
      ),
    );
  }

  List<Widget> prepareAttachments(item) {
    return item["attachments"]
            ?.map((a) {
              var aspectRatio = a["aspectRatio"];
              var url = a["url"];
              var type = a["type"];

              if (type == "IMAGE") {
                return PhotoAttachment(
                  url: url,
                  aspectRatio: aspectRatio,
                );
              } else if (type == "VIDEO") {
                return VideoAttachment(
                  url: url,
                  aspectRatio: aspectRatio,
                );
              } else {
                return Container();
              }
            })
            ?.cast<Widget>()
            ?.toList() ??
        [];
  }

  void onTapEllipsis(context) {
    infoToast("Пока не реализовано", context);
  }
}
