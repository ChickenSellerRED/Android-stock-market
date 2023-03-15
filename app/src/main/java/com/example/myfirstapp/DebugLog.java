package com.example.myfirstapp;

public class DebugLog {

// There is a bug in 2023/03/08:
//     When I use glide to load a remote image file to the stock detail‘s logo(which is an ImageView)
//         At first it can not load, but other remote images in news title and body can be loaded normally.
//        So I check the urls of images, I found the logo image is in svg format and old version glide cannot deal with it
//        So I check glide's github and found the solution: add some custom class(SvgDecoder SvgModule and so much) to
//        load svg from glide.
//     But then I found the svg can not fix the image View's size(height and width).
//        So I print the SVG class's information when decode it in glide. And I found that SVG's viewbox is null when reading from inputstream
//        So I initialize the viewbox manually. Now the imageView could load svg perfectly

}
