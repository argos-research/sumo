function setEmissionClass(vehID, clazz)
%setEmissionClass Sets the emission class for this vehicle.
%   setEmissionClass(VEHID,CLASS) Sets the emission class for this vehicle.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setEmissionClass.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
traci.sendStringCmd(constants.CMD_SET_VEHICLE_VARIABLE, constants.VAR_EMISSIONCLASS, vehID, clazz);