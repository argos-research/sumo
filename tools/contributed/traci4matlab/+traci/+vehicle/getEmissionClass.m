function emissionClass = getEmissionClass(vehID)
%getEmissionClass Returns the emission class of this vehicle.
%   emissionClass = getEmissionClass(VEHID) Returns the emission class of 
%   this vehicle.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getEmissionClass.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
emissionClass = traci.vehicle.getUniversal(constants.VAR_EMISSIONCLASS, vehID);