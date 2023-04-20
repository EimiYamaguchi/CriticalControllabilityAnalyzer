# CriticalControllabilityAnalyzer

CPMDS（critical probabilistic minimum dominating set）を求めるプログラムです。

# 3Dアニメーション
40-59歳の男性のHC脳領域のデータで構築したタンパク質ネットワークを可視化したものです。
赤い頂点がCPMDSアルゴリズムで同定したCriticalタンパク質。

![CriticalPMDS](https://user-images.githubusercontent.com/104329344/233333508-6fc1d7e6-79fe-4d91-940d-6fcc29d7358f.gif)


# Usage
## 必要なもの
*Javaの開発環境の構築<br>
*IBM ILOG CPLEX Optimization (https://www.ibm.com/products/ilog-cplex-optimization-studio)<br>
使用したCplexのバージョンは12.8.0.0です。<br>

## 実行方法
1.	PMDS_CriticalContorollabilityAnalyzerのsrcのディレクトリに移動します。

2.	以下のコマンドを実行
```
>javac -encoding UTF-8 Main.java
>java Main
```

3.	プログラムの指示に従って、エッジファイルが存在するフォルダのPath、結果を保存するフォルダのPath、パラメータの３つを入力してください。<br>
Pathは絶対パスで入力してください。<br>パラメータθは0<θ<1の範囲で指定してください。
## example
```
Input folder path(absolute path)  > C:\Users\xxx\xxx\Example\input
Result folder path(absolute path)  > C:\Users\ xxx\xxx \Example\result
Parameter(0 < θ < 1)  > 0.5
```


# Note

学生時代に書いたプログラムなので大変お見苦しいと思われます。。。
いつか書き直そうと思って５年経ちました（遠い目）

