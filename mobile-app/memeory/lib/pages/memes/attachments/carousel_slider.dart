import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';

class AttachmentCarousel extends StatefulWidget {
  const AttachmentCarousel({
    Key key,
    @required this.items,
    this.minAspectRatio = 1.0,
  }) : super(key: key);

  final List<Widget> items;
  final double minAspectRatio;

  @override
  _AttachmentCarouselState createState() => _AttachmentCarouselState();
}

class _AttachmentCarouselState extends State<AttachmentCarousel> {
  int _current = 0;

  @override
  Widget build(BuildContext context) {
    if (widget.items.length == 0) {
      return Container();
    } else if (widget.items.length == 1) {
      return widget.items[0];
    }

    return Column(
      children: <Widget>[
        CarouselSlider(
          aspectRatio: widget.minAspectRatio,
          viewportFraction: 1.0,
          items: widget.items,
          enableInfiniteScroll: false,
          onPageChanged: (index) {
            setState(() {
              _current = index;
            });
          },
        ),
        Container(
          padding: EdgeInsets.only(top: 10),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: widget.items
                .asMap()
                .map((index, value) {
                  var isActive = _current == index;
                  return MapEntry(
                    index,
                    Container(
                      width: isActive ? 10.0 : 8.0,
                      height: isActive ? 10.0 : 8.0,
                      margin: EdgeInsets.symmetric(horizontal: 2.0),
                      decoration: BoxDecoration(
                        shape: BoxShape.circle,
                        color: isActive
                            ? Theme.of(context).accentColor
                            : Colors.grey,
                      ),
                    ),
                  );
                })
                .values
                .toList(),
          ),
        ),
      ],
    );
  }
}
