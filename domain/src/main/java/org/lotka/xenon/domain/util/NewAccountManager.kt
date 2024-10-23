package org.lotka.xenon.domain.util


import android.app.Activity
import android.content.Context
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.PasswordCredential
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import org.lotka.xenon.domain.util.CredentialSignInResult
import org.lotka.xenon.domain.util.CredentialSignUpResult
import javax.inject.Inject

class NewAccountManager  @Inject constructor(
    private val context: Context
) {
    private val credentialManager = CredentialManager.create(context)

    suspend fun signUp(email: String, password: String): CredentialSignUpResult {
        return try {
            credentialManager.createCredential(
                context = context,
                request = CreatePasswordRequest(
                    id = email,
                    password = password
                )
            )
            CredentialSignUpResult.Success(email)
        } catch (e: CreateCredentialCancellationException) {
            e.printStackTrace()
            CredentialSignUpResult.Cancelled
        } catch(e: CreateCredentialException) {
            e.printStackTrace()
            CredentialSignUpResult.Failure
        }
    }

    suspend fun signIn(): CredentialSignInResult {
        return try {
            val credentialResponse = credentialManager.getCredential(
                context = context,
                request = GetCredentialRequest(
                    credentialOptions = listOf(GetPasswordOption())
                )
            )

            val credential = credentialResponse.credential as? PasswordCredential
                ?: return CredentialSignInResult.Failure

            // Make login API call here with credential.id and credential.password

            CredentialSignInResult.Success(credential.id)
        } catch(e: GetCredentialCancellationException) {
            e.printStackTrace()
            CredentialSignInResult.Cancelled
        } catch(e: NoCredentialException) {
            e.printStackTrace()
            CredentialSignInResult.NoCredentials
        } catch(e: GetCredentialException) {
            e.printStackTrace()
            CredentialSignInResult.Failure
        }
    }
}