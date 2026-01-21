# 🚀 Release Setup Guide

This document explains how to set up automated releases for the Klokk project.

## Prerequisites

1. A GitHub repository with the Klokk project
2. An Android keystore for signing APKs (optional, but recommended for production)

## Setting Up GitHub Secrets

### For Android APK Signing (Optional)

If you want to sign your Android APK, you need to set up the following secrets in your GitHub repository:

1. Go to your GitHub repository
2. Click on **Settings** → **Secrets and variables** → **Actions**
3. Click **New repository secret** and add the following secrets:

#### Required Secrets:

| Secret Name | Description | How to Get |
|-------------|-------------|------------|
| `SIGNING_KEY` | Base64 encoded keystore file | See below |
| `ALIAS` | Keystore alias | Your keystore alias |
| `KEY_STORE_PASSWORD` | Keystore password | Your keystore password |
| `KEY_PASSWORD` | Key password | Your key password |

#### How to Generate Base64 Keystore:

```bash
# If you don't have a keystore, create one:
keytool -genkey -v -keystore release.keystore -alias klokk -keyalg RSA -keysize 2048 -validity 10000

# Convert to base64
base64 -i release.keystore | pbcopy  # macOS
# or
base64 release.keystore | xclip -selection clipboard  # Linux
# or
certutil -encode release.keystore encoded.txt  # Windows
```

### For Unsigned Releases (Simpler Option)

If you don't want to set up signing, you can remove the signing step from the workflow:

1. Edit `.github/workflows/release.yml`
2. Remove the "Sign APK" step (lines ~30-40)
3. Update the "Rename APK" step to use the unsigned APK:
   ```yaml
   - name: Rename APK
     run: |
       mv app/build/outputs/apk/release/app-release-unsigned.apk klokk-android.apk
   ```

## Creating a Release

### 1. Update Version

Update the version in `app/build.gradle.kts`:

```kotlin
defaultConfig {
    versionCode = 2  // Increment this
    versionName = "1.0.2"  // Update this
}
```

### 2. Commit Your Changes

```bash
git add .
git commit -m "Release v1.0.2"
```

### 3. Create and Push a Tag

```bash
git tag v1.0.2
git push origin v1.0.2
```

### 4. Wait for the Build

The GitHub Actions workflow will automatically:
- Build Android APK (signed if secrets are set up)
- Build Windows MSI installer
- Build macOS DMG package
- Build Linux DEB package
- Generate release notes from commit messages
- Create a GitHub release with all binaries

### 5. Check the Release

1. Go to your repository on GitHub
2. Click on **Releases** (right sidebar)
3. You should see your new release with all the binaries attached

## Release Notes

The workflow automatically generates release notes based on commit messages since the last tag. To get clean release notes:

### Good Commit Messages:
```
Add immersive mode support
Fix screen orientation on tablets
Update Android TV banner
Improve performance on low-end devices
```

### Bad Commit Messages:
```
fix bug
update
wip
test
```

## Troubleshooting

### Build Fails

Check the **Actions** tab in your GitHub repository to see detailed logs.

Common issues:
- Missing secrets (if using signing)
- Gradle build errors (test locally first)
- Out of memory (increase heap size in gradle.properties)

### APK Not Signed

If you see "app-release-unsigned.apk" in the artifacts:
- Check that all signing secrets are set correctly
- Verify the keystore is valid
- Check the workflow logs for signing errors

### Desktop Packages Not Created

Some platforms require specific runners:
- Windows MSI: Requires Windows runner
- macOS DMG: Requires macOS runner (expensive on GitHub Actions)
- Linux DEB: Works on Ubuntu runner

## Testing Locally

Before creating a release, test the builds locally:

```bash
# Test Android build
./gradlew :app:assembleRelease

# Test Desktop builds
./gradlew :desktopApp:packageDmg      # macOS
./gradlew :desktopApp:packageMsi      # Windows
./gradlew :desktopApp:packageDeb      # Linux
```

## Cost Considerations

GitHub Actions minutes usage:
- Ubuntu runners: 2,000 free minutes/month
- Windows runners: 2,000 free minutes/month (1 minute = 2 minutes)
- macOS runners: 2,000 free minutes/month (1 minute = 10 minutes)

**Tip**: macOS builds are expensive. Consider building macOS packages manually if you're on a free plan.

## Manual Release (Alternative)

If you prefer manual releases:

1. Build all packages locally
2. Go to **Releases** → **Draft a new release**
3. Create a tag (e.g., v1.0.2)
4. Upload the binaries
5. Write release notes
6. Click **Publish release**
