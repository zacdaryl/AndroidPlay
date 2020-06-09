import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
//
//class Foo extends StatefulWidget {
//  @override
//  Widget build(BuildContext context) {
//    return Scaffold(
//      body: Center(
//        child: Text('hello!'),
//      ),
//    );
//  }
//
//  @override
//  State<StatefulWidget> createState() {
//    // TODO: implement createState
//    throw UnimplementedError();
//  }
//}

class Foo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return FooState();
  }
}

class FooState extends State<Foo> {

  var _ver = "hello";

  @override
  void initState() {
    super.initState();

    const msgChannel = const BasicMessageChannel('basic_msg_channel', StandardMessageCodec());
    msgChannel.setMessageHandler((message) => Future(() async{
      setState(() {
        _ver = message["a"];
      });
      return "";
    }));
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Text(_ver),
    );
  }

}
