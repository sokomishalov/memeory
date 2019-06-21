import 'package:flutter/material.dart';
import 'package:loadmore/loadmore.dart';
import 'package:memeory/api/memes.dart';
import 'package:memeory/pages/memes/attachments/photo.dart';
import 'package:memeory/pages/memes/attachments/video.dart';

class MemesVertical extends StatefulWidget {
  @override
  _MemesVerticalState createState() => _MemesVerticalState();
}

class _MemesVerticalState extends State<MemesVertical> {
  int _currentPage;
  List _memes;

  @override
  void initState() {
    _currentPage = -1;
    _memes = [];
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: LoadMore(
        textBuilder: loadingTextBuilder,
        delegate: DefaultLoadMoreDelegate(),
        onLoadMore: onLoadMore,
        child: ListView.builder(
          itemCount: _memes?.length ?? 0,
          itemBuilder: (context, index) {
            var item = _memes[index];
            var attachments = item["attachments"]?.map((a) {
              if (a["type"] == "IMAGE") {
                return PhotoAttachment(url: a["url"]);
              } else if (a["type"] == "VIDEO") {
                return VideoAttachment(url: a["url"]);
              } else {
                return Container();
              }
            })?.toList();

            return Column(
              key: Key(item["id"]),
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Padding(
                  padding: EdgeInsets.all(8),
                  child: Opacity(
                    child: Text(
                      item["channel"],
                      style: TextStyle(fontSize: 12),
                    ),
                    opacity: 0.5,
                  ),
                ),
                Padding(
                  padding: EdgeInsets.all(8),
                  child: Text(
                    item["caption"],
                    style: TextStyle(fontSize: 16),
                  ),
                ),
                ...attachments,
                Padding(
                  padding: EdgeInsets.only(top: 8),
                  child: Divider(
                    color: Theme.of(context).accentColor,
                  ),
                )
              ],
            );
          },
        ),
      ),
    );
  }

  Future<bool> onLoadMore() async {
    var newPage = _currentPage + 1;
    var newMemes = []..addAll(_memes)..addAll(await fetchMemes(newPage));

    debugPrint("loaded page #$newPage");

    setState(() {
      _currentPage = newPage;
      _memes = newMemes;
    });

    return true;
  }

  String loadingTextBuilder(LoadMoreStatus status) {
    String text;
    switch (status) {
      case LoadMoreStatus.fail:
        text = "Ошибка при загрузке мемесов :(";
        break;
      case LoadMoreStatus.idle:
      case LoadMoreStatus.loading:
        text = "Подождите, гружу мемесы ...";
        break;
      case LoadMoreStatus.nomore:
      default:
        text = "Нет больше мемесов :(";
        break;
    }
    return text;
  }
}
