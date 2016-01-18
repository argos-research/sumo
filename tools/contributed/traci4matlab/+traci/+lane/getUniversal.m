function returnedValue = getUniversal(varID, laneID)
%getUniversal An internal function to send the get command and read the 
%variable value.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getUniversal.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
global laneSubscriptionResults

if isempty(laneSubscriptionResults)
    returnValueFunc = traci.RETURN_VALUE_FUNC.lane;
else
    returnValueFunc = laneSubscriptionResults.valueFunc;
end

% Prepare the outgoing message and read the response. The result variable
% is a traci.Storage object
result = traci.sendReadOneStringCmd(constants.CMD_GET_LANE_VARIABLE,varID,laneID);
handleReturValueFunc = str2func(returnValueFunc(varID));

% Use the proper method to read the variable of interest from the result
returnedValue = handleReturValueFunc(result);