function emissionClass = getEmissionClass(vehID)
%getEmissionClass Returns the emission class of this vehicle.
%   emissionClass = getEmissionClass(VEHID) Returns the emission class of 
%   this vehicle.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
emissionClass = traci.vehicle.getUniversal(constants.VAR_EMISSIONCLASS, vehID);