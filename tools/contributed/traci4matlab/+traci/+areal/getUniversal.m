function returnedValue = getUniversal(varID, detID)
%getUniversal An internal function to send the get command and read the 
%variable value.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getUniversal.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
global arealSubscriptionResults

if isempty(arealSubscriptionResults)
    ReturnValueFunc = traci.RETURN_VALUE_FUNC.areal;
else
    ReturnValueFunc = aeralSubscriptionResults.valueFunc;
end

% Prepare the outgoing message and read the response. The result variable
% is a traci.Storage object
result = traci.sendReadOneStringCmd(constants.CMD_GET_AREAL_DETECTOR_VARIABLE,varID,detID);
handleReturValueFunc = str2func(ReturnValueFunc(varID));

% Use the proper method to read the variable of interest from the result
returnedValue = handleReturValueFunc(result);