module.exports = {
  presets: [
    ['@babel/preset-react', { runtime: 'automatic', importSource: '@emotion/react' }],
    '@babel/preset-env',
    '@babel/preset-typescript',
  ],
  plugins: ['@emotion/babel-plugin'],
};
