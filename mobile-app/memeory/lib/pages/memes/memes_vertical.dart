import 'package:flutter/material.dart';
import 'package:memeory/api/memes.dart';
import 'package:memeory/common/containers/future_builder.dart';
import 'package:memeory/pages/memes/attachments/photo.dart';

class MemesVertical extends StatefulWidget {
  @override
  _MemesVerticalState createState() => _MemesVerticalState();
}

class _MemesVerticalState extends State<MemesVertical> {
  Future _memes;

  @override
  void initState() {
    _memes = fetchMemes(0);
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: FutureWidget(
        future: _memes,
        render: (data) {
          return ListView.builder(
            itemCount: data?.length ?? 0,
            itemBuilder: (context, index) {
              var item = data[index];
              var attachments = item["attachments"]
                  ?.map((a) => PhotoAttachment(
                        url: a["url"],
                      ))
                  ?.cast<Widget>()
                  ?.toList();

              return Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Container(
                    padding: EdgeInsets.symmetric(vertical: 5, horizontal: 10),
                    child: Text(item["caption"]),
                  ),
                  ...attachments
                ],
              );
            },
          );
        },
      ),
    );
  }
}
