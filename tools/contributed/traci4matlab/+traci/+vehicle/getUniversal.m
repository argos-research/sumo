function returnedValue = getUniversal(varID, vehID)
%getUniversal An internal function to send the get command and read the 
%variable value.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getUniversal.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
global vehSubscriptionResults

if isempty(vehSubscriptionResults)
    returnValueFunc = traci.RETURN_VALUE_FUNC.vehicle;
else
    returnValueFunc = vehSubscriptionResults.valueFunc;
end

% Prepare the outgoing message and read the response. The result variable
% is a traci.Storage object
result = traci.sendReadOneStringCmd(constants.CMD_GET_VEHICLE_VARIABLE,varID,vehID);
handleReturValueFunc = str2func(returnValueFunc(varID));

% Use the proper method to read the variable of interest from the result
returnedValue = handleReturValueFunc(result);