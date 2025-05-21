# 📈 Stock Screener Frontend

This is the React-based frontend for the Real-Time Intraday Stock Screener. It visualizes low-float stocks, volume spikes, float rotation, catalysts, entry/exit signals, and more.

## 🚀 Features

- 🔍 Live stock screener with auto-refresh
- 🧠 Entry/Exit suggestion engine
- 📰 Ticker-specific news
- 📊 Material UI interface with sort/filter/search
- ➕ Add/remove stocks via modal form
- 🔁 30-second auto-refresh interval

## 📦 Tech Stack

- React
- Material UI
- Axios
- Chart.js (optional)
- Tailwind CSS (optional utility support)

## 📂 Project Structure
src/
├── components/
│ ├── ScreenerTable.js
│ ├── AddStockModal.js
│ └── NewsList.js
├── App.js
├── api/
│ └── stockService.js
└── index.js

## 🔧 Getting Started

### 1. Install dependencies
```
npm install
```

### 2. Start development server
```
npm start
```
The app runs on: http://localhost:3000

###⚙️ Configuration
Make sure the backend URL is correctly set in axios.get() or create an .env file:
```
REACT_APP_API_BASE_URL=http://localhost:8000
```
Update your Axios calls accordingly using process.env.REACT_APP_API_BASE_URL.

### 📌 TODO
 - Add WebSocket for real-time updates
 - Implement login/auth for watchlist syncing


### Available Scripts

In the project directory, you can run:

#### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

The page will reload when you make changes.\
You may also see any lint errors in the console.

#### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

#### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

#### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can't go back!**

If you aren't satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you're on your own.

You don't have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn't feel obligated to use this feature. However we understand that this tool wouldn't be useful if you couldn't customize it when you are ready for it.

### Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).

### Code Splitting

This section has moved here: [https://facebook.github.io/create-react-app/docs/code-splitting](https://facebook.github.io/create-react-app/docs/code-splitting)

### Analyzing the Bundle Size

This section has moved here: [https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size](https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size)

### Making a Progressive Web App

This section has moved here: [https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app](https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app)

### Advanced Configuration

This section has moved here: [https://facebook.github.io/create-react-app/docs/advanced-configuration](https://facebook.github.io/create-react-app/docs/advanced-configuration)

### Deployment

This section has moved here: [https://facebook.github.io/create-react-app/docs/deployment](https://facebook.github.io/create-react-app/docs/deployment)

### `npm run build` fails to minify

This section has moved here: [https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify](https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify)
