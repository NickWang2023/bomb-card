# 💣 炸弹卡片 (Bomb Card)

一个基于"心灵点滴"内容的正能量卡片应用，每天随机展示一张心灵炸弹，引爆你的正能量！

## ✨ 功能特性

- 🎴 **随机卡片**：每日随机展示一张心灵炸弹卡片
- 🏷️ **智能标签**：每张卡片配有1-2个分类标签
- 📅 **日期展示**：显示公历和农历日期
- 🔄 **换一张**：随时更换新的心灵炸弹
- 🌤️ **天气预报**：底部天气占位组件
- 🎨 **深橘色主题**：愉悦色调，正向轻松

## 🛠️ 技术栈

### Android App
- Kotlin
- Jetpack Compose
- Material Design 3

### Backend
- FastAPI (Python)
- Docker

## 🚀 快速开始

### 构建 APK
```bash
./gradlew assembleRelease
```

### 启动后端
```bash
cd backend
pip install -r requirements.txt
python main.py
```

## 📁 项目结构

```
炸弹卡片-app/
├── app/                    # Android 应用
│   ├── src/main/java/      # 源代码
│   └── src/main/res/       # 资源文件
├── backend/                # 后端服务
│   ├── main.py            # FastAPI 主程序
│   └── requirements.txt   # Python 依赖
└── .github/workflows/      # CI/CD 配置
```

## 📝 更新日志

### v1.0.0
- 初始版本发布
- 基础卡片展示功能
- 农历日期显示
- 天气占位组件

## 👨‍💻 开发者

- GitHub: [NickWang2023](https://github.com/NickWang2023)

## 📄 许可证

MIT License